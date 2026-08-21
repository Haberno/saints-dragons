package com.leon.saintsdragons.client.renderer.volitans;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.SaintsDragonsDeferredEntityRenderer;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansWaterBreathEntity;
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

public class VolitansWaterBreathRenderer extends SaintsDragonsDeferredEntityRenderer<VolitansWaterBreathEntity> {
    private static final float SPRITE_WIDTH_PX = 32.0F;
    private static final float SPRITE_HEIGHT_PX = 32.0F;
    private static final int TOTAL_FRAMES = 5;
    private static final Identifier[] WATER_TEXTURES = new Identifier[TOTAL_FRAMES];
    private static final Identifier[] POISON_TEXTURES = new Identifier[TOTAL_FRAMES];

    static {
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            WATER_TEXTURES[i] = SaintsDragonsCommon.rl("textures/entity/volitans/water" + i + ".png");
            POISON_TEXTURES[i] = SaintsDragonsCommon.rl("textures/entity/volitans/poison" + i + ".png");
        }
    }

    public VolitansWaterBreathRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    protected void submitEntity(VolitansWaterBreathEntity entity, RenderState<VolitansWaterBreathEntity> renderState,
                                PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                                CameraRenderState cameraState) {
        float partialTicks = renderState.partialTick;
        float age = entity.getAge() + partialTicks;
        float life = Math.max(1.0F, entity.getMaxAge());
        float normalized = Mth.clamp(age / life, 0.0F, 1.0F);
        int frame = ((int) (age / 3.0F)) % TOTAL_FRAMES;
        if (frame < 0) {
            frame += TOTAL_FRAMES;
        }
        Identifier texture = entity.isPoisonMode() ? POISON_TEXTURES[frame] : WATER_TEXTURES[frame];
        float alpha = Mth.lerp(normalized, 1.0F, 0.82F);
        float scale = Mth.lerp(normalized, 0.34F, 0.72F);

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(this.entityRenderDispatcher.camera.rotation());

        float halfWidth = SPRITE_WIDTH_PX / 32.0F;
        float halfHeight = SPRITE_HEIGHT_PX / 32.0F;
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(texture), (pose, vc) -> {
            addVertex(vc, pose.pose(), pose.normal(), -halfWidth, -halfHeight, 0.0F, 0.0F, 1.0F, alpha);
            addVertex(vc, pose.pose(), pose.normal(), -halfWidth, halfHeight, 0.0F, 0.0F, 0.0F, alpha);
            addVertex(vc, pose.pose(), pose.normal(), halfWidth, halfHeight, 0.0F, 1.0F, 0.0F, alpha);
            addVertex(vc, pose.pose(), pose.normal(), halfWidth, -halfHeight, 0.0F, 1.0F, 1.0F, alpha);
        });

        poseStack.popPose();
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
