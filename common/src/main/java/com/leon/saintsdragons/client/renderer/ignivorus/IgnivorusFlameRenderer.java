package com.leon.saintsdragons.client.renderer.ignivorus;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.ignivorus.IgnivorusFlameEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;


public class IgnivorusFlameRenderer extends SaintsDragonsDeferredEntityRenderer<IgnivorusFlameEntity> {

    private static final int TOTAL_FRAMES = 5;
    private static final float FLAME_RENDER_SCALE = 0.65F;
    private static final float SPAWN_START_SCALE_FACTOR = 0.18F;
    private static final float SPAWN_GROWTH_TICKS = 5.0F;
    private static final Identifier[] TEXTURES = new Identifier[TOTAL_FRAMES];

    static {
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            TEXTURES[i] = SaintsDragonsCommon.rl("textures/entity/ignivorus/fireparticle" + i + ".png");
        }
    }
    public IgnivorusFlameRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(IgnivorusFlameEntity entity, RenderState<IgnivorusFlameEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        float partialTicks = renderState.partialTick;
        int age = entity.getAge();
        int frame = (age / 2) % TOTAL_FRAMES;

        Identifier texture = TEXTURES[frame];
        float baseScale = entity.getScale() * FLAME_RENDER_SCALE;
        float ageWithPartial = Math.max(0.0F, age + partialTicks);
        float spawnProgress = Mth.clamp(ageWithPartial / SPAWN_GROWTH_TICKS, 0.0F, 1.0F);
        float spawnScaleFactor = Mth.lerp(spawnProgress, SPAWN_START_SCALE_FACTOR, 1.0F);
        float scale = baseScale * spawnScaleFactor;
        float alpha = 1.0F;
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(this.entityRenderDispatcher.camera.rotation());
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityCutoutNoCull(texture),
                (pose, vertexConsumer) -> renderBillboard(
                        vertexConsumer, pose.pose(), pose.normal(), renderState.lightCoords, alpha));
        poseStack.popPose();
    }

    private void renderBillboard(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int packedLight, float alpha) {
        float size = 1.0F;

        addVertex(consumer, matrix4f, matrix3f, -size, -size, 0.0F, 0.0F, 1.0F, alpha);
        addVertex(consumer, matrix4f, matrix3f, -size, size, 0.0F, 0.0F, 0.0F, alpha);
        addVertex(consumer, matrix4f, matrix3f, size, size, 0.0F, 1.0F, 0.0F, alpha);
        addVertex(consumer, matrix4f, matrix3f, size, -size, 0.0F, 1.0F, 1.0F, alpha);
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f,
                          float x, float y, float z, float u, float v, float alpha) {
        consumer.addVertex(matrix4f, x, y, z)
                .setColor(1.0F, 1.0F, 1.0F, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(240)
                .setNormal(0.0F, 0.0F, 1.0F);
    }

}
