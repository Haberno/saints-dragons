package com.leon.saintsdragons.client.model.draconianswarm;

import com.leon.saintsdragons.client.model.LegacyAnimationState;
import com.leon.saintsdragons.client.model.LegacyEntityGeoModel;
import com.leon.saintsdragons.client.model.LegacyEntityModelData;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.draconianswarm.Latcher;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LatcherModel extends LegacyEntityGeoModel<Latcher> {
    private static final Identifier MODEL =
            SaintsDragonsCommon.rl("entity/latcher");
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/draconian_swarm/latcher/latcher.png");
    private static final Identifier ANIMATIONS =
            SaintsDragonsCommon.rl("entity/latcher");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(Latcher animatable) {
        return ANIMATIONS;
    }

    @Override
    protected void setCustomAnimations(Latcher entity, long instanceId, LegacyAnimationState<Latcher> animationState) {
        if (!entity.isAlive()) {
            return;
        }

        float partialTick = animationState.renderState().getPartialTick();
        float pitchRad = Mth.clamp(entity.getFlightPitchRadians(partialTick), -0.95F, 0.95F);
        float speed = Mth.clamp((float) entity.getDeltaMovement().length(), 0.0F, 0.7F);
        float dragYaw = Mth.clamp(entity.getTailDragYawRadians(partialTick), -0.95F, 0.95F);
        float swayYaw = Mth.sin((entity.tickCount + partialTick) * 0.18F) * speed * 0.12F;

        applyFlightPitch(pitchRad);
        applyLook(animationState.entityModelData());
        applyTailDrag("secondrot", dragYaw * 0.85F + swayYaw);
        applyTailDrag("thirdrot", dragYaw * 1.25F + swayYaw * 1.25F);
        applyTailDrag("forthrot", dragYaw * 1.35F + swayYaw * 1.35F);
    }

    private void applyFlightPitch(float pitchRad) {
        getBone("root").ifPresent(bone -> bone.setRotX(bone.getRotX() + pitchRad));
    }

    private void applyLook(LegacyEntityModelData modelData) {
        float lookPitchRad = Mth.clamp(modelData.headPitch() * Mth.DEG_TO_RAD, -0.65F, 0.65F);
        float lookYawRad = Mth.clamp(modelData.netHeadYaw() * Mth.DEG_TO_RAD, -0.85F, 0.85F);
        applyLookRotation("neck", lookPitchRad * 0.35F, lookYawRad * 0.40F);
        applyLookRotation("head", lookPitchRad * 0.65F, lookYawRad * 0.70F);
    }

    private void applyLookRotation(String boneName, float pitchRad, float yawRad) {
        getBone(boneName).ifPresent(bone -> {
            bone.setRotX(bone.getRotX() + pitchRad);
            bone.setRotY(bone.getRotY() + yawRad);
        });
    }

    private void applyTailDrag(String boneName, float yawRad) {
        getBone(boneName).ifPresent(bone -> bone.setRotY(bone.getRotY() + yawRad));
    }
}
