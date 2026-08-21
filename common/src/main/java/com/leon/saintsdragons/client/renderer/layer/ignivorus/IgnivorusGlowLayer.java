package com.leon.saintsdragons.client.renderer.layer.ignivorus;

import com.leon.saintsdragons.client.renderer.layer.DynamicTextureGeoLayer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.base.GeoRenderer;

public class IgnivorusGlowLayer extends DynamicTextureGeoLayer<Ignivorus> {
    private static final Identifier GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/ignivorus/ignivorus_glow.png");
    private static final Identifier FEMALE_GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/ignivorus/ignivorus_glow_female.png");
    private static final Identifier CRIMSON_GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/ignivorus/crimson_ignivorus_glow.png");

    public IgnivorusGlowLayer(
            GeoRenderer<Ignivorus, Void, SaintsDragonsLivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    protected LayerRenderData getLayerRenderData(Ignivorus animatable, float partialTick) {
        boolean breathingFire = animatable.isBreathingFire();
        boolean chargingFireball = animatable.isChargingFireball();
        if (!breathingFire && !chargingFireball) {
            return null;
        }

        float ticks = animatable.tickCount + partialTick;
        float alpha;
        if (chargingFireball) {
            int chargeLevel = animatable.getFireballChargeLevel();
            float chargeIntensity = chargeLevel / 3.0F;
            float pulseSpeed = 0.15F + chargeLevel * 0.1F;
            float pulse = 0.5F + 0.5F * Mth.sin(ticks * pulseSpeed);
            alpha = chargeIntensity * (0.4F + 0.6F * pulse);
            if (chargeLevel == 3) {
                float rapidPulse = 0.8F + 0.2F * Mth.sin(ticks * 0.8F);
                alpha = Math.min(1.0F, alpha * rapidPulse * 1.2F);
            }
        } else {
            float pulse = 0.5F + 0.5F * Mth.sin(ticks * 0.2F);
            float streamProgress = animatable.getFireBreathProgress() / 40.0F;
            alpha = Mth.clamp(streamProgress, 0.0F, 1.0F) * (0.35F + 0.65F * pulse);
        }

        if (alpha <= 0.01F) {
            return null;
        }
        return new LayerRenderData(getGlowTexture(animatable), ARGB.white(alpha), 0xF000F0);
    }

    private static Identifier getGlowTexture(Ignivorus animatable) {
        if (animatable.getTextureVariant() == Ignivorus.VARIANT_CRIMSON) {
            return CRIMSON_GLOW_TEXTURE;
        }
        return animatable.isFemale() ? FEMALE_GLOW_TEXTURE : GLOW_TEXTURE;
    }
}
