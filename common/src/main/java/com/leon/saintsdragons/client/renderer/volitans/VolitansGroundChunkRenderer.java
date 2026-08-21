package com.leon.saintsdragons.client.renderer.volitans;

import com.leon.saintsdragons.client.model.volitans.VolitansGroundChunkModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsEntityGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsEntityRenderState;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansGroundChunkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.constant.dataticket.DataTicket;

public class VolitansGroundChunkRenderer extends SaintsDragonsEntityGeoRenderer<VolitansGroundChunkEntity> {
    private static final DataTicket<Boolean> READY = DataTicket.create("saintsdragons_ground_chunk_ready", Boolean.class);

    public VolitansGroundChunkRenderer(EntityRendererProvider.Context context) {
        super(context, new VolitansGroundChunkModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void addRenderData(VolitansGroundChunkEntity animatable, Void relatedObject,
                              SaintsDragonsEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(READY, animatable.isReady());
    }

    @Override
    protected float calculateYRot(VolitansGroundChunkEntity entity, float yRot, float partialTick) {
        return entity.getVisualYaw();
    }

    @Override
    public RenderType getRenderType(SaintsDragonsEntityRenderState renderState, Identifier texture) {
        return renderState.getOrDefaultGeckolibData(READY, false)
                ? super.getRenderType(renderState, texture)
                : null;
    }
}
