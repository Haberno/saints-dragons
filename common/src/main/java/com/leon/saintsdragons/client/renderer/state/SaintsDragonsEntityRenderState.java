package com.leon.saintsdragons.client.renderer.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Concrete GeckoLib render state for non-living mod entities.
 *
 * <p>GeckoLib normally adds {@link GeoRenderState} to vanilla render states with a mixin.
 * A concrete state keeps our renderer generic types explicit and avoids depending on a
 * runtime-only interface cast in source code.</p>
 */
public class SaintsDragonsEntityRenderState extends EntityRenderState implements GeoRenderState {
    private final Map<DataTicket<?>, Object> geckolibData = new IdentityHashMap<>();

    @Override
    public Map<DataTicket<?>, Object> getDataMap() {
        return geckolibData;
    }

    @Override
    public <D> void addGeckolibData(DataTicket<D> dataTicket, D data) {
        this.geckolibData.put(dataTicket, data);
    }

    @Override
    public boolean hasGeckolibData(DataTicket<?> dataTicket) {
        return this.geckolibData.containsKey(dataTicket);
    }

    @Override
    public int getPackedLight() {
        return getOrDefaultGeckolibData(DataTickets.PACKED_LIGHT, lightCoords);
    }

    @Override
    public double getAnimatableAge() {
        return getOrDefaultGeckolibData(DataTickets.TICK, (double) ageInTicks);
    }
}
