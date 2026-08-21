package com.leon.saintsdragons.client.renderer.raevyx;

import com.leon.saintsdragons.server.entity.effect.raevyx.RaevyxLightningChainEntity;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

/**
 * Renderer for LightningChainEntity.
 * Since this entity is purely visual and manages its own particles,
 * we don't need to render anything here - just prevent the null renderer crash.
 */
@Environment(EnvType.CLIENT)
public class RaevyxLightningChainRenderer extends SaintsDragonsDeferredEntityRenderer<RaevyxLightningChainEntity> {

    public RaevyxLightningChainRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void submitEntity(RaevyxLightningChainEntity entity, RenderState<RaevyxLightningChainEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        // Particle-only visual entity.
    }
}
