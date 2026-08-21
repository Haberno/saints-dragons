package com.leon.saintsdragons.client.renderer.stegonaut;

import com.leon.saintsdragons.server.entity.effect.stegonaut.StegonautGroundChunkEntity;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StegonautGroundChunkRenderer extends SaintsDragonsDeferredEntityRenderer<StegonautGroundChunkEntity> {

    public StegonautGroundChunkRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    protected void submitEntity(StegonautGroundChunkEntity entity, RenderState<StegonautGroundChunkEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        BlockState state = entity.getBlockState();
        if (state.isAir()) {
            return;
        }

        float scale = entity.getVisualScale();
        poseStack.pushPose();
        poseStack.translate(-0.5D * scale, 0.0D, -0.5D * scale);
        poseStack.scale(scale, scale, scale);
        submitNodeCollector.submitBlock(poseStack, state, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
