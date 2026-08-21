package com.leon.saintsdragons.client.renderer.ignivorus;

import com.leon.saintsdragons.server.entity.effect.ignivorus.IgnivorusMagmaBlockEntity;
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

public class IgnivorusMagmaBlockRenderer extends SaintsDragonsDeferredEntityRenderer<IgnivorusMagmaBlockEntity> {

    public IgnivorusMagmaBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.6F;
    }

    @Override
    protected void submitEntity(IgnivorusMagmaBlockEntity entity, RenderState<IgnivorusMagmaBlockEntity> renderState,
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
