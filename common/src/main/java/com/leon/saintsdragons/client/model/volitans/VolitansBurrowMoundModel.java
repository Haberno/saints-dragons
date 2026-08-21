package com.leon.saintsdragons.client.model.volitans;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansBurrowMoundEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class VolitansBurrowMoundModel extends GeoModel<VolitansBurrowMoundEntity> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("geckolib/models/blocks/burrow_mound.geo.json");
    private static final Identifier TEXTURE = SaintsDragonsCommon.rl("textures/blocks/burrow_mound.png");
    private static final Identifier ANIMATION = SaintsDragonsCommon.rl("geckolib/animations/blocks/burrow_mound.animation.json");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(VolitansBurrowMoundEntity animatable) {
        return ANIMATION;
    }
}
