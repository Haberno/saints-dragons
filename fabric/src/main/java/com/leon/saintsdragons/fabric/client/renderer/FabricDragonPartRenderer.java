package com.leon.saintsdragons.fabric.client.renderer;

import com.leon.saintsdragons.fabric.entity.part.FabricDragonPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class FabricDragonPartRenderer extends EntityRenderer<FabricDragonPart, EntityRenderState> {

    public FabricDragonPartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
