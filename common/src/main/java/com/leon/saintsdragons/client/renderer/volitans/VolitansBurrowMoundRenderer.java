package com.leon.saintsdragons.client.renderer.volitans;

import com.leon.saintsdragons.client.model.volitans.VolitansBurrowMoundModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsEntityGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsEntityRenderState;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansBurrowMoundEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class VolitansBurrowMoundRenderer extends SaintsDragonsEntityGeoRenderer<VolitansBurrowMoundEntity> {
    public VolitansBurrowMoundRenderer(EntityRendererProvider.Context context) {
        super(context, new VolitansBurrowMoundModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    protected float calculateYRot(VolitansBurrowMoundEntity entity, float yRot, float partialTick) {
        return entity.getVisualYaw();
    }

    @Override
    public int getRenderColor(VolitansBurrowMoundEntity animatable, Void relatedObject, float partialTick) {
        return ARGB.white(animatable.getOpacity(partialTick));
    }

    @Override
    public RenderType getRenderType(SaintsDragonsEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}
