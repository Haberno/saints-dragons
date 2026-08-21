package com.leon.saintsdragons.client.renderer.draconianswarm;

import com.leon.saintsdragons.client.model.draconianswarm.LatcherModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsLivingGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.server.entity.draconianswarm.Latcher;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LatcherRenderer extends SaintsDragonsLivingGeoRenderer<Latcher> {
    public LatcherRenderer(EntityRendererProvider.Context context) {
        super(context, new LatcherModel());
        this.shadowRadius = 0.45F;
    }

    @Override
    public void addRenderData(Latcher animatable, Void relatedObject,
                              SaintsDragonsLivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(DataTickets.PACKED_LIGHT, LightTexture.FULL_BRIGHT);
    }

    @Override
    public RenderType getRenderType(SaintsDragonsLivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityCutout(texture);
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }
}
