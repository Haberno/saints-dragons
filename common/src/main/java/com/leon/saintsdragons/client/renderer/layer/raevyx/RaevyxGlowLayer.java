package com.leon.saintsdragons.client.renderer.layer.raevyx;

import com.leon.saintsdragons.client.renderer.layer.DynamicTextureGeoLayer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.dragons.raevyx.Raevyx;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.base.GeoRenderer;

public class RaevyxGlowLayer extends DynamicTextureGeoLayer<Raevyx> {
    private static final Identifier GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/raevyx/raevyx_glow.png");
    private static final Identifier FEMALE_GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/raevyx/raevyx_female_glow.png");
    private static final Identifier NIGHT_GOLD_GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/raevyx/raevyx_night_gold_glow.png");
    private static final Identifier NIGHT_GOLD_FEMALE_GLOW_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/raevyx/raevyx_night_gold_female_glow.png");

    public RaevyxGlowLayer(
            GeoRenderer<Raevyx, Void, SaintsDragonsLivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    protected LayerRenderData getLayerRenderData(Raevyx animatable, float partialTick) {
        if (!animatable.isBeamGlowActive()) {
            return null;
        }

        float ticks = animatable.tickCount + partialTick;
        float alpha = 0.5F + 0.5F * Mth.sin(ticks * 0.12F);
        boolean nightGold = animatable.getTextureVariant() == Raevyx.VARIANT_NIGHT_GOLD;
        Identifier texture = nightGold
                ? (animatable.isFemale() ? NIGHT_GOLD_FEMALE_GLOW_TEXTURE : NIGHT_GOLD_GLOW_TEXTURE)
                : (animatable.isFemale() ? FEMALE_GLOW_TEXTURE : GLOW_TEXTURE);
        return new LayerRenderData(texture, ARGB.white(alpha), 0xF000F0);
    }
}
