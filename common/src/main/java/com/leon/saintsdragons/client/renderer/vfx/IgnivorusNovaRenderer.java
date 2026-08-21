package com.leon.saintsdragons.client.renderer.vfx;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.ignivorus.IgnivorusNovaEntity;
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

public class IgnivorusNovaRenderer extends SaintsDragonsDeferredEntityRenderer<IgnivorusNovaEntity> {

    private static final int TOTAL_FRAMES = 8;
    private static final Identifier[] TEXTURES = new Identifier[TOTAL_FRAMES];

    static {
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            TEXTURES[i] = SaintsDragonsCommon.rl("textures/entity/ignivorus/nova" + i + ".png");
        }
    }

    public IgnivorusNovaRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(IgnivorusNovaEntity entity, RenderState<IgnivorusNovaEntity> renderState,
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

        float s = scale * 16.0F;
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(texture), (pose, consumer) -> {
            Matrix4f matrix = pose.pose();
            Matrix3f normal = pose.normal();
            addFace(consumer, matrix, normal, -s, s, s, s, s, s, s, -s, s, -s, -s, s, 0, 0, 1, opacity);
            addFace(consumer, matrix, normal, s, s, -s, -s, s, -s, -s, -s, -s, s, -s, -s, 0, 0, -1, opacity);
            addFace(consumer, matrix, normal, s, s, s, s, s, -s, s, -s, -s, s, -s, s, 1, 0, 0, opacity);
            addFace(consumer, matrix, normal, -s, s, -s, -s, s, s, -s, -s, s, -s, -s, -s, -1, 0, 0, opacity);
            addFace(consumer, matrix, normal, -s, s, -s, s, s, -s, s, s, s, -s, s, s, 0, 1, 0, opacity);
            addFace(consumer, matrix, normal, s, -s, -s, -s, -s, -s, -s, -s, s, s, -s, s, 0, -1, 0, opacity);
        });

        poseStack.popPose();
    }

    private void addFace(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix,
                         float x1, float y1, float z1,
                         float x2, float y2, float z2,
                         float x3, float y3, float z3,
                         float x4, float y4, float z4,
                         float nx, float ny, float nz, float opacity) {

        consumer.addVertex(matrix, x1, y1, z1)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(nx, ny, nz);

        consumer.addVertex(matrix, x2, y2, z2)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(nx, ny, nz);

        consumer.addVertex(matrix, x3, y3, z3)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(1, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(nx, ny, nz);

        consumer.addVertex(matrix, x4, y4, z4)
                .setColor(1.0F, 1.0F, 1.0F, opacity)
                .setUv(0, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(nx, ny, nz);
    }

}
