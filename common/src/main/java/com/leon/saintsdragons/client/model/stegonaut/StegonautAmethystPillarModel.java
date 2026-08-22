package com.leon.saintsdragons.client.model.stegonaut;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.stegonaut.StegonautAmethystPillarEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class StegonautAmethystPillarModel extends GeoModel<StegonautAmethystPillarEntity> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("blocks/amethyst_pillar");
    private static final Identifier TEXTURE = SaintsDragonsCommon.rl("textures/blocks/amethyst_pillar.png");
    private static final Identifier ANIMATION = SaintsDragonsCommon.rl("blocks/amethyst_pillar");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(StegonautAmethystPillarEntity animatable) {
        return ANIMATION;
    }
}
