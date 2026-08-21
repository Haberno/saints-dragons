package com.leon.saintsdragons.client.renderer.stegonaut;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.GroundCrackEntity;
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

public class StegonautGroundCrackRenderer extends SaintsDragonsDeferredEntityRenderer<GroundCrackEntity> {
    private static final Identifier STEGONAUT_TEXTURE = SaintsDragonsCommon.rl("textures/particle/ground_crack.png");
    private static final Identifier DRAGONLORD_FISSURE_TEXTURE = SaintsDragonsCommon.rl("textures/particle/ground_crack_fissure.png");

    public StegonautGroundCrackRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(GroundCrackEntity entity, RenderState<GroundCrackEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        float partialTicks = renderState.partialTick;
        float opacity = entity.getOpacity(partialTicks);
        if (opacity <= 0.001F) {
            return;
        }

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        Identifier texture = entity.isDragonlordFissure() ? DRAGONLORD_FISSURE_TEXTURE : STEGONAUT_TEXTURE;
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(texture),
                (pose, consumer) -> renderHorizontalSquare(consumer, pose.pose(), pose.normal(),
                        entity.getScale(partialTicks), opacity));
        poseStack.popPose();
    }

    private void renderHorizontalSquare(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix,
                                        float size, float opacity) {
        Vector3f normal = new Vector3f(0.0F, 1.0F, 0.0F);
        normalMatrix.transform(normal);
        float y = GroundCrackEntity.RENDER_PLANE_Y;

        consumer.addVertex(matrix, -size, y, -size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(matrix, -size, y, size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(matrix, size, y, size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(matrix, size, y, -size)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normal.x(), normal.y(), normal.z());
    }

}
