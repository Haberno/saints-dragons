package com.leon.saintsdragons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.Entity;

/** Small bridge for custom renderers while preserving 1.21.11's deferred submission contract. */
public abstract class SaintsDragonsDeferredEntityRenderer<T extends Entity>
        extends EntityRenderer<T, SaintsDragonsDeferredEntityRenderer.RenderState<T>> {
    protected SaintsDragonsDeferredEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public RenderState<T> createRenderState() {
        return new RenderState<>();
    }

    @Override
    public void extractRenderState(T entity, RenderState<T> state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.entity = entity;
        state.partialTick = partialTick;
        state.entityYaw = entity.getYRot();
    }

    @Override
    public final void submit(RenderState<T> state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                             CameraRenderState cameraState) {
        submitEntity(state.entity, state, poseStack, submitNodeCollector, cameraState);
        super.submit(state, poseStack, submitNodeCollector, cameraState);
    }

    protected abstract void submitEntity(T entity, RenderState<T> state, PoseStack poseStack,
                                         SubmitNodeCollector submitNodeCollector, CameraRenderState cameraState);

    public static final class RenderState<T extends Entity> extends EntityRenderState {
        public T entity;
        public float partialTick;
        public float entityYaw;
    }
}
