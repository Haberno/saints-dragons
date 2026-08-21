package com.leon.saintsdragons.client.model.ignivorus;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.ignivorus.IgnivorusMagmaPillarEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class IgnivorusMagmaPillarModel extends GeoModel<IgnivorusMagmaPillarEntity> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("geckolib/models/blocks/ignivorus_magma_pillar.geo.json");
    private static final Identifier TEXTURE = SaintsDragonsCommon.rl("textures/blocks/ignivorus_magma_pillar.png");
    private static final Identifier ANIMATION = SaintsDragonsCommon.rl("geckolib/animations/blocks/ignivorus_magma_pillar.animation.json");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(IgnivorusMagmaPillarEntity animatable) {
        return ANIMATION;
    }
}
