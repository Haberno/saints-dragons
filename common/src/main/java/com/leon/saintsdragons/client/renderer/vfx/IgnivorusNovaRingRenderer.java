package com.leon.saintsdragons.client.renderer.vfx;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.ignivorus.IgnivorusNovaRingEntity;
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
import org.joml.Vector3f;

public class IgnivorusNovaRingRenderer extends SaintsDragonsDeferredEntityRenderer<IgnivorusNovaRingEntity> {

    private static final int TOTAL_FRAMES = 5;
    private static final Identifier[] TEXTURES = new Identifier[TOTAL_FRAMES];

    static {
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            TEXTURES[i] = SaintsDragonsCommon.rl("textures/entity/ignivorus/ring" + i + ".png");
        }
    }

    public IgnivorusNovaRingRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(IgnivorusNovaRingEntity entity, RenderState<IgnivorusNovaRingEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        float partialTicks = renderState.partialTick;

        float scale = entity.getScale(partialTicks);
        float opacity = entity.getOpacity(partialTicks);

        if (opacity <= 0.001F) {
            return;
        }

        poseStack.pushPose();

        int frame = (entity.getAge() * TOTAL_FRAMES) / entity.getDuration();
        frame = Math.min(frame, TOTAL_FRAMES - 1);
        Identifier texture = TEXTURES[frame];

        float size = scale * 16.0F;
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(texture),
                (pose, consumer) -> renderHorizontalSquare(consumer, pose.pose(), pose.normal(), size, opacity));

        poseStack.popPose();
    }

    private void renderHorizontalSquare(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix,
                                        float size, float opacity) {
        float half = size;

        Vector3f normalVec = new Vector3f(0.0F, 1.0F, 0.0F);
        normalMatrix.transform(normalVec);

        float y = 0.1F;

        consumer.addVertex(matrix, -half, y, -half)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normalVec.x(), normalVec.y(), normalVec.z());

        consumer.addVertex(matrix, -half, y, half)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normalVec.x(), normalVec.y(), normalVec.z());

        consumer.addVertex(matrix, half, y, half)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normalVec.x(), normalVec.y(), normalVec.z());

        consumer.addVertex(matrix, half, y, -half)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(normalVec.x(), normalVec.y(), normalVec.z());
    }

}
