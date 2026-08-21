package com.leon.saintsdragons.client.renderer.layer;

import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.server.entity.base.DragonEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.base.GeoRenderer;

public abstract class NightEmissiveLayer<T extends DragonEntity> extends DynamicTextureGeoLayer<T> {
    private static final long DUSK_START = 12000L;
    private static final long NIGHT_FULL = 13000L;
    private static final long NIGHT_FADE = 22500L;
    private static final long DAWN_END = 23500L;

    protected NightEmissiveLayer(
            GeoRenderer<T, Void, SaintsDragonsLivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    protected LayerRenderData getLayerRenderData(T animatable, float partialTick) {
        if (animatable.isBaby()) {
            return null;
        }
        if (animatable.hasCustomTextureVariant() && !allowCustomTextureVariantEmissive(animatable)) {
            return null;
        }

        float alpha = getNightAlpha(animatable);
        Identifier texture = getEmissiveTexture(animatable);
        if (alpha <= 0.01F || texture == null) {
            return null;
        }
        return new LayerRenderData(texture, ARGB.white(alpha), 0xF000F0);
    }

    protected abstract @Nullable Identifier getEmissiveTexture(T animatable);

    protected boolean allowCustomTextureVariantEmissive(T animatable) {
        return false;
    }

    private static float getNightAlpha(DragonEntity animatable) {
        long dayTime = animatable.level().getDayTime() % 24000L;
        if (dayTime < DUSK_START || dayTime > DAWN_END) {
            return 0.0F;
        }
        if (dayTime < NIGHT_FULL) {
            return Mth.clamp((dayTime - DUSK_START) / (float) (NIGHT_FULL - DUSK_START), 0.0F, 1.0F);
        }
        if (dayTime > NIGHT_FADE) {
            return Mth.clamp((DAWN_END - dayTime) / (float) (DAWN_END - NIGHT_FADE), 0.0F, 1.0F);
        }
        return 1.0F;
    }
}
