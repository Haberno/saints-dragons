package com.leon.saintsdragons.client.model;

import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderState;

/** Minimal bridge used while preserving the mod's GeckoLib 4 custom-pose code on GeckoLib 5. */
public final class LegacyAnimationState<T> {
    private final GeoRenderState renderState;

    public LegacyAnimationState(GeoRenderState renderState) {
        this.renderState = renderState;
    }

    public GeoRenderState renderState() {
        return renderState;
    }

    public LegacyEntityModelData entityModelData() {
        return new LegacyEntityModelData(
                renderState.getOrDefaultGeckolibData(DataTickets.ENTITY_PITCH, 0.0F),
                renderState.getOrDefaultGeckolibData(DataTickets.ENTITY_YAW, 0.0F)
        );
    }
}
