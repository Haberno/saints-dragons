package com.leon.saintsdragons.client.renderer.otheranimals;

import com.leon.saintsdragons.client.model.otheranimals.MoopModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsLivingGeoRenderer;
import com.leon.saintsdragons.server.entity.otheranimals.Moop;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MoopRenderer extends SaintsDragonsLivingGeoRenderer<Moop> {
    public MoopRenderer(EntityRendererProvider.Context context) {
        super(context, new MoopModel());
        this.shadowRadius = 0.25F;
    }
}
