package com.leon.saintsdragons.client.renderer.draconianswarm;

import com.leon.saintsdragons.client.model.draconianswarm.WingedModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsLivingGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.server.entity.draconianswarm.Winged;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class WingedRenderer extends SaintsDragonsLivingGeoRenderer<Winged> {
    public WingedRenderer(EntityRendererProvider.Context context) {
        super(context, new WingedModel());
        this.shadowRadius = 0.35F;
    }

    @Override
    public void addRenderData(Winged animatable, Void relatedObject,
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
