package com.leon.saintsdragons.client.renderer.cindervane;

import com.leon.saintsdragons.server.entity.effect.cindervane.CindervaneMagmaBlockEntity;
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

public class CindervaneMagmaBlockRenderer extends SaintsDragonsDeferredEntityRenderer<CindervaneMagmaBlockEntity> {

    public CindervaneMagmaBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.6F;
    }

    @Override
    protected void submitEntity(CindervaneMagmaBlockEntity entity, RenderState<CindervaneMagmaBlockEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        BlockState state = entity.getBlockState();
        if (state.isAir()) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(-0.5D, 0.0D, -0.5D);
        submitNodeCollector.submitBlock(poseStack, state, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}

