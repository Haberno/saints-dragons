package com.leon.saintsdragons.client.renderer.npc;

import com.leon.saintsdragons.client.model.npc.IvyTheDragonMerchantModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsLivingGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.client.renderer.layer.npc.IvyHeldItemLayer;
import com.leon.saintsdragons.common.registry.ModSounds;
import com.leon.saintsdragons.server.entity.npc.IvyTheDragonMerchant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class IvyTheDragonMerchantRenderer extends SaintsDragonsLivingGeoRenderer<IvyTheDragonMerchant> {
    private static final double CHATTER_RENDER_DISTANCE_SQR = 256.0D;
    private static final float CHATTER_Y_OFFSET = 0.15F;
    private static final long CHATTER_TYPE_INTERVAL_MS = 42L;
    private static final long VOICE_BLIP_INTERVAL_MS = 34L;
    private final Map<IvyTheDragonMerchant, ChatterRenderState> chatterStates = new WeakHashMap<>();

    @Override
    public float getMotionAnimThreshold(IvyTheDragonMerchant animatable) {
        return 0.000001f;
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    public IvyTheDragonMerchantRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new IvyTheDragonMerchantModel());
        this.withRenderLayer(new IvyHeldItemLayer(this));
        this.shadowRadius = 0.6f;
    }

    @Override
    public void extractRenderState(IvyTheDragonMerchant entity,
                                   SaintsDragonsLivingEntityRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        String chatter = entity.getIdleChatterText();
        if (chatter.isEmpty()
                || renderState.distanceToCameraSq > CHATTER_RENDER_DISTANCE_SQR) {
            return;
        }
        chatter = resolveChatterText(entity, chatter);
        ChatterRenderState state = getChatterState(entity, chatter);
        String visibleChatter = state.visibleText();
        if (visibleChatter.isEmpty()) {
            return;
        }
        playVoiceBlipForNewText(visibleChatter, state);
        renderState.nameTag = Component.literal(visibleChatter);
        renderState.nameTagAttachment = new Vec3(0.0D, entity.getBbHeight() + CHATTER_Y_OFFSET, 0.0D);
    }

    private ChatterRenderState getChatterState(IvyTheDragonMerchant entity, String text) {
        ChatterRenderState state = chatterStates.get(entity);
        if (state == null || !state.text.equals(text)) {
            state = new ChatterRenderState(text);
            chatterStates.put(entity, state);
        }
        return state;
    }

    private static String resolveChatterText(IvyTheDragonMerchant entity, String translationKey) {
        String text = net.minecraft.network.chat.Component.translatable(translationKey).getString();
        String name = entity.getIdleChatterName();
        return text.replace("{name}", name);
    }

    private void playVoiceBlipForNewText(String visibleText, ChatterRenderState state) {
        long now = System.currentTimeMillis();
        if (now - state.lastVoiceBlipTime < VOICE_BLIP_INTERVAL_MS) {
            return;
        }
        int visibleCodePoints = visibleText.codePointCount(0, visibleText.length());
        if (visibleCodePoints <= state.lastBlipCodePoints) {
            return;
        }
        int totalCodePoints = state.text.codePointCount(0, state.text.length());
        int safeVisibleCodePoints = Math.min(visibleCodePoints, totalCodePoints);
        int playableIndex = -1;
        for (int index = state.lastBlipCodePoints; index < safeVisibleCodePoints; index++) {
            int charIndex = state.text.offsetByCodePoints(0, index);
            int codePoint = state.text.codePointAt(charIndex);
            if (shouldPlayVoiceBlip(codePoint, index)) {
                playableIndex = index;
                break;
            }
        }
        if (playableIndex >= 0) {
            float pitch = (float) ThreadLocalRandom.current().nextDouble(0.94D, 1.07D);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.IVY_VOICE_BLIP.get(), pitch, 0.42F));
            state.lastVoiceBlipTime = now;
            state.lastBlipCodePoints = playableIndex + 1;
            return;
        }
        state.lastBlipCodePoints = safeVisibleCodePoints;
    }

    private static boolean shouldPlayVoiceBlip(int codePoint, int codePointIndex) {
        if (Character.isWhitespace(codePoint)) {
            return false;
        }
        if (".,!?;:()[]{}\"'".indexOf(codePoint) >= 0) {
            return false;
        }
        return codePointIndex % 2 == 0;
    }

    private static final class ChatterRenderState {
        private final String text;
        private final long startedAt;
        private long lastVoiceBlipTime;
        private int lastBlipCodePoints;

        private ChatterRenderState(String text) {
            this.text = text;
            this.startedAt = System.currentTimeMillis();
        }

        private String visibleText() {
            int totalCodePoints = text.codePointCount(0, text.length());
            int visibleCodePoints = Math.min(totalCodePoints, (int) ((System.currentTimeMillis() - startedAt) / CHATTER_TYPE_INTERVAL_MS));
            if (visibleCodePoints <= 0) {
                return "";
            }
            return text.substring(0, text.offsetByCodePoints(0, visibleCodePoints));
        }
    }

}
