package com.leon.saintsdragons.client.model.volitans;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.volitans.ArrowOfVenomEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ArrowOfVenomModel extends GeoModel<ArrowOfVenomEntity> {
    private static final Identifier MODEL =
            SaintsDragonsCommon.rl("geckolib/models/entity/arrow_of_venom.geo.json");
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/arrow_of_venom.png");
    private static final Identifier ANIMATION =
            SaintsDragonsCommon.rl("geckolib/animations/entity/arrow_of_venom.animation.json");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(ArrowOfVenomEntity animatable) {
        return ANIMATION;
    }
}
