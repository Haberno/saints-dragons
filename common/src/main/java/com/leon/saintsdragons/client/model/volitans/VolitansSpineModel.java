package com.leon.saintsdragons.client.model.volitans;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansSpineEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class VolitansSpineModel extends GeoModel<VolitansSpineEntity> {
    private static final Identifier MODEL =
            SaintsDragonsCommon.rl("geckolib/models/entity/volitans_spine.geo.json");
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/volitans/volitans_spine.png");
    private static final Identifier ANIMATION =
            SaintsDragonsCommon.rl("geckolib/animations/entity/volitans_spine.animation.json");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(VolitansSpineEntity animatable) {
        return ANIMATION;
    }
}
