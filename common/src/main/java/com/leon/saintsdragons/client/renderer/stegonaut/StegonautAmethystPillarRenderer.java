package com.leon.saintsdragons.client.renderer.stegonaut;

import com.leon.saintsdragons.client.model.stegonaut.StegonautAmethystPillarModel;
import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import com.leon.saintsdragons.client.renderer.SaintsDragonsEntityGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsEntityRenderState;
import com.leon.saintsdragons.server.entity.effect.stegonaut.StegonautAmethystPillarEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class StegonautAmethystPillarRenderer
        extends SaintsDragonsEntityGeoRenderer<StegonautAmethystPillarEntity> {
    public StegonautAmethystPillarRenderer(EntityRendererProvider.Context context) {
        super(context, new StegonautAmethystPillarModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<SaintsDragonsEntityRenderState> renderPassInfo) {
        super.adjustRenderPose(renderPassInfo);
        StegonautAmethystPillarEntity entity =
                (StegonautAmethystPillarEntity) renderPassInfo.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
        if (entity != null) {
            float scale = entity.getVisualScale();
            renderPassInfo.poseStack().scale(scale, scale, scale);
        }
    }
}
