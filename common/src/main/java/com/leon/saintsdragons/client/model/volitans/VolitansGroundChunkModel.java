package com.leon.saintsdragons.client.model.volitans;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansGroundChunkEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class VolitansGroundChunkModel extends GeoModel<VolitansGroundChunkEntity> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("blocks/ground_chunk");
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("minecraft", "textures/block/dirt.png");
    private static final Identifier ANIMATION = SaintsDragonsCommon.rl("blocks/ground_chunk");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(VolitansGroundChunkEntity animatable) {
        return ANIMATION;
    }
}
