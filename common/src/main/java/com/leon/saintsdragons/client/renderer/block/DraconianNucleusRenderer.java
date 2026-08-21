package com.leon.saintsdragons.client.renderer.block;

import com.leon.saintsdragons.client.model.block.DraconianNucleusAnimations;
import com.leon.saintsdragons.client.model.block.DraconianNucleusModel;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.block.DraconianNucleusBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class DraconianNucleusRenderer implements BlockEntityRenderer<DraconianNucleusBlockEntity, DraconianNucleusRenderer.RenderState> {
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/block/draconic_nucleus.png");
    private final DraconianNucleusModel model;

    public DraconianNucleusRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new DraconianNucleusModel(context.bakeLayer(DraconianNucleusModel.LAYER_LOCATION));
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(DraconianNucleusBlockEntity nucleus, RenderState state, float partialTick,
                                   net.minecraft.world.phys.Vec3 cameraPos,
                                   net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderState.extractBase(nucleus, state, breakProgress);
        state.active = nucleus.hasActiveEncounter();
        state.animationTimeMillis = nucleus.getAnimationTimeMillis(partialTick);
    }

    @Override
    public void submit(RenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                       CameraRenderState cameraState) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);
        submitNodeCollector.submitModel(model, state, poseStack, RenderTypes.entityTranslucent(TEXTURE),
                state.lightCoords, OverlayTexture.NO_OVERLAY, -1, state.breakProgress);
        poseStack.popPose();
    }

    public static final class RenderState extends BlockEntityRenderState {
        public boolean active;
        public long animationTimeMillis;
    }
}
