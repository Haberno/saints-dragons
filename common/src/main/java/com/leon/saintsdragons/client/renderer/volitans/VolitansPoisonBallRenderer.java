package com.leon.saintsdragons.client.renderer.volitans;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansPoisonBallEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class VolitansPoisonBallRenderer extends SaintsDragonsDeferredEntityRenderer<VolitansPoisonBallEntity> {
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/volitans/poison_ball.png");

    public VolitansPoisonBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(VolitansPoisonBallEntity entity, RenderState<VolitansPoisonBallEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        float scale = entity.getVisualScale() * 0.45F;

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(this.entityRenderDispatcher.camera.rotation());

        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(TEXTURE), (pose, vc) -> {
            addVertex(vc, pose.pose(), pose.normal(), -1.0F, -1.0F, 0.0F, 0.0F, 1.0F);
            addVertex(vc, pose.pose(), pose.normal(), -1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            addVertex(vc, pose.pose(), pose.normal(), 1.0F, 1.0F, 0.0F, 1.0F, 0.0F);
            addVertex(vc, pose.pose(), pose.normal(), 1.0F, -1.0F, 0.0F, 1.0F, 1.0F);
        });

        poseStack.popPose();
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f,
                           float x, float y, float z, float u, float v) {
        consumer.addVertex(matrix4f, x, y, z)
                .setColor(1.0F, 1.0F, 1.0F, 1.0F)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(0.0F, 0.0F, 1.0F);
    }

}

