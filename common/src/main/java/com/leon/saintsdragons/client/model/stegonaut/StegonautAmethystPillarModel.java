package com.leon.saintsdragons.client.model.stegonaut;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.stegonaut.StegonautAmethystPillarEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class StegonautAmethystPillarModel extends GeoModel<StegonautAmethystPillarEntity> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("geo/blocks/amethyst_pillar.geo.json");
    private static final Identifier TEXTURE = SaintsDragonsCommon.rl("textures/blocks/amethyst_pillar.png");
    private static final Identifier ANIMATION = SaintsDragonsCommon.rl("animations/blocks/amethyst_pillar.animation.json");

    @Override
    public Identifier getModelResource(StegonautAmethystPillarEntity animatable) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(StegonautAmethystPillarEntity animatable) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(StegonautAmethystPillarEntity animatable) {
        return ANIMATION;
    }
}
