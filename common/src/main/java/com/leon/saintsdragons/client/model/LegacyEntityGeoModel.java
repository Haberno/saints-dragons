package com.leon.saintsdragons.client.model;

import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

import javax.annotation.Nullable;
import java.util.Optional;

/** GeckoLib 5 bridge for non-dragon entity models with custom bone poses. */
public abstract class LegacyEntityGeoModel<T extends Entity & GeoAnimatable> extends GeoModel<T>
        implements CustomBonePoseModel<T> {
    private BoneSnapshots currentSnapshots;

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        renderState.addGeckolibData(GeoRenderDataTickets.ANIMATABLE, animatable);
    }

    @SuppressWarnings("unchecked")
    protected final T getAnimatable(GeoRenderState renderState) {
        return (T) renderState.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
    }

    protected final Optional<BoneSnapshot> getBone(String boneName) {
        return currentSnapshots == null ? Optional.empty() : currentSnapshots.get(boneName);
    }

    protected void setCustomAnimations(T entity, long instanceId, LegacyAnimationState<T> animationState) {
    }

    @Override
    public final void applyCustomBonePose(T entity, RenderPassInfo<? extends GeoRenderState> renderPassInfo,
                                          BoneSnapshots snapshots) {
        currentSnapshots = snapshots;
        try {
            setCustomAnimations(entity, entity.getId(), new LegacyAnimationState<>(renderPassInfo.renderState()));
        } finally {
            currentSnapshots = null;
        }
    }
}
