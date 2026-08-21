package com.leon.saintsdragons.client.renderer.ignivorus;

import com.leon.saintsdragons.client.model.ignivorus.IgnivorusMagmaPillarModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsEntityGeoRenderer;
import com.leon.saintsdragons.server.entity.effect.ignivorus.IgnivorusMagmaPillarEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class IgnivorusMagmaPillarRenderer extends SaintsDragonsEntityGeoRenderer<IgnivorusMagmaPillarEntity> {
    public IgnivorusMagmaPillarRenderer(EntityRendererProvider.Context context) {
        super(context, new IgnivorusMagmaPillarModel());
        this.shadowRadius = 1.0F;
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }
}
