package com.leon.saintsdragons.client.renderer.vfx;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.DragonWaterSplashEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DragonWaterSplashRenderer extends SaintsDragonsDeferredEntityRenderer<DragonWaterSplashEntity> {
    private static final Identifier[] TEXTURES = {
            SaintsDragonsCommon.rl("textures/particle/watersplash0.png"),
            SaintsDragonsCommon.rl("textures/particle/watersplash1.png"),
            SaintsDragonsCommon.rl("textures/particle/watersplash2.png"),
            SaintsDragonsCommon.rl("textures/particle/watersplash3.png")
    };

    public DragonWaterSplashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(DragonWaterSplashEntity entity, RenderState<DragonWaterSplashEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        float partialTicks = renderState.partialTick;
        float opacity = entity.getOpacity(partialTicks);
        if (opacity <= 0.001F) {
            return;
        }

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        int frame = entity.getAnimationFrame(partialTicks);
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(TEXTURES[frame]),
                (pose, consumer) -> renderFrame(consumer, pose.pose(), pose.normal(), frame,
                        entity.getScale(partialTicks), opacity));
        poseStack.popPose();
    }

    private void renderFrame(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix,
                             int frame, float size, float opacity) {
        if (frame == 0) {
            renderHorizontalSquare(consumer, matrix, normalMatrix, 0.0F, 0.0F, size * 0.85F, opacity);
            return;
        }

        float spread = size * (0.35F + frame * 0.38F);
        float frameSize = size * (0.78F + frame * 0.08F);
        renderHorizontalSquare(consumer, matrix, normalMatrix, -spread, 0.0F, frameSize, opacity);
        renderHorizontalSquare(consumer, matrix, normalMatrix, spread, 0.0F, frameSize, opacity);
    }

    private void renderHorizontalSquare(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix,
                                        float centerX, float centerZ, float size, float opacity) {
        Vector3f normal = new Vector3f(0.0F, 1.0F, 0.0F);
        normalMatrix.transform(normal);
        float y = 0.035F;

        consumer.addVertex(matrix, centerX - size, y, centerZ - size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(matrix, centerX - size, y, centerZ + size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(matrix, centerX + size, y, centerZ + size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(matrix, centerX + size, y, centerZ - size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
    }

}
