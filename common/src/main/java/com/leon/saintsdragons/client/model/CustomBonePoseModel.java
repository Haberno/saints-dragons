package com.leon.saintsdragons.client.model;

import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

/** GeckoLib 5 replacement for the removed GeoModel#setCustomAnimations hook. */
public interface CustomBonePoseModel<T extends Entity> {
    void applyCustomBonePose(T entity, RenderPassInfo<? extends GeoRenderState> renderPassInfo,
                             BoneSnapshots snapshots);
}
