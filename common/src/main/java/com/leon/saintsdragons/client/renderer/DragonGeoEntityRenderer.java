package com.leon.saintsdragons.client.renderer;

import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.client.renderer.vfx.DragonDiveTrailRenderer;
import com.leon.saintsdragons.server.entity.base.RideableDragonBase;
import com.leon.saintsdragons.server.entity.base.RideableFlyingDragon;
import com.leon.saintsdragons.server.entity.base.RideableGroundDragon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

import java.util.HashMap;
import java.util.Map;

/** Shared GeckoLib 5 renderer behavior for rideable dragons. */
public abstract class DragonGeoEntityRenderer<T extends RideableDragonBase>
        extends SaintsDragonsLivingGeoRenderer<T> {
    private static final double DIVE_TRAIL_RENDER_DISTANCE = 256.0D;
    private static final double DIVE_TRAIL_CULL_PADDING = 48.0D;
    private final Map<String, Vec3> boneWorldPositions = new HashMap<>();

    protected DragonGeoEntityRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public float getMotionAnimThreshold(T animatable) {
        return 0.000001F;
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public void addRenderData(T animatable, Void relatedObject,
                              SaintsDragonsLivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.shadowRadius = animatable.isBaby()
                ? getBabyShadowRadius(animatable)
                : getAdultShadowRadius(animatable);
        boneWorldPositions.clear();
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<SaintsDragonsLivingEntityRenderState> renderPassInfo,
                                    float widthScale, float heightScale) {
        T animatable = getAnimatable(renderPassInfo);
        float scale = animatable == null ? 1.0F : getRenderScale(animatable);
        super.scaleModelForRender(renderPassInfo, widthScale * scale, heightScale * scale);
    }

    @Override
    public void preRenderPass(RenderPassInfo<SaintsDragonsLivingEntityRenderState> renderPassInfo,
                              SubmitNodeCollector renderTasks) {
        T animatable = getAnimatable(renderPassInfo);
        if (animatable == null || EntityPreviewRenderContext.isRendering()) {
            return;
        }

        for (LocatorSpec spec : locatorSpecs(animatable)) {
            renderPassInfo.addBonePositionListener(spec.boneName(), (worldPos, modelPos, localPos) -> {
                if (worldPos == null) {
                    return;
                }
                Vec3 position = worldPos.add(spec.x() / 16.0D, spec.y() / 16.0D, spec.z() / 16.0D);
                boneWorldPositions.put(spec.boneName(), position);
                for (String locatorName : spec.locatorNames()) {
                    animatable.setClientLocatorPosition(locatorName, position);
                }
                captureRiderCameraIfNeeded(animatable, spec.boneName(), position);
            });
        }
    }

    @Override
    public RenderType getRenderType(SaintsDragonsLivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityCutoutNoCull(texture);
    }

    @Override
    public boolean shouldRender(@NotNull T entity, @NotNull Frustum frustum,
                                double camX, double camY, double camZ) {
        if (super.shouldRender(entity, frustum, camX, camY, camZ)) {
            return true;
        }

        double dx = entity.getX() - camX;
        double dy = entity.getY() - camY;
        double dz = entity.getZ() - camZ;
        double maxDistance = DIVE_TRAIL_RENDER_DISTANCE * DIVE_TRAIL_RENDER_DISTANCE;
        if (dx * dx + dy * dy + dz * dz > maxDistance
                || DragonDiveTrailRenderer.getTrailIntensity(entity) <= 0.0F) {
            return false;
        }

        AABB trailBounds = entity.getBoundingBox().inflate(DIVE_TRAIL_CULL_PADDING);
        return frustum.isVisible(trailBounds);
    }

    protected float getRenderScale(T entity) {
        return 1.0F;
    }

    protected abstract float getBabyShadowRadius(T entity);

    protected abstract float getAdultShadowRadius(T entity);

    protected String[] trackedBoneNames() {
        return new String[0];
    }

    protected LocatorSpec[] locatorSpecs(T entity) {
        return new LocatorSpec[0];
    }

    /** Compatibility hook retained for subclasses while trail submission is moved separately. */
    protected void afterDragonRender(T entity, PoseStack poseStack,
                                     MultiBufferSource bufferSource, float partialTick) {
    }

    protected Vec3 getBoneWorldPosition(String boneName) {
        return boneWorldPositions.get(boneName);
    }

    protected int seatIndexForRiderBone(T animatable, String boneName, RiderConfig.RiderSpec riderSpec) {
        return boneName.equals(riderSpec.boneName) ? 0 : -1;
    }

    private void captureRiderCameraIfNeeded(T animatable, String boneName, Vec3 cameraWorldPos) {
        RiderConfig.RiderSpec riderSpec = RiderConfig.getSpec(animatable);
        if (riderSpec == null) {
            return;
        }

        int seatIndex = seatIndexForRiderBone(animatable, boneName, riderSpec);
        if (seatIndex < 0 || !RiderBullcrap.tryLockForFrame(animatable, seatIndex)) {
            return;
        }

        if (!usesGroundedRawFirstPersonBoneAnchor(animatable)) {
            org.joml.Vector3f offset = RiderConfig.getFirstPersonOffset(animatable, seatIndex);
            cameraWorldPos = cameraWorldPos.add(offset.x(), offset.y(), offset.z());
        }
        RiderBullcrap.store(animatable, seatIndex, new Matrix4f(), cameraWorldPos.subtract(animatable.position()));
    }

    private boolean usesGroundedRawFirstPersonBoneAnchor(T animatable) {
        if (animatable instanceof RideableFlyingDragon) {
            return !animatable.isFlying()
                    && !animatable.isTakeoff()
                    && !animatable.isLanding()
                    && !animatable.isHovering();
        }
        return animatable instanceof RideableGroundDragon;
    }

    @SuppressWarnings("unchecked")
    private T getAnimatable(RenderPassInfo<SaintsDragonsLivingEntityRenderState> renderPassInfo) {
        return (T) renderPassInfo.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
    }

    public record LocatorSpec(String boneName, float x, float y, float z, String... locatorNames) {
    }
}
