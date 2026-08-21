package com.leon.saintsdragons.client.renderer.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.IdentityHashMap;
import java.util.Map;

/** Concrete GeckoLib render state for living mod entities. */
public class SaintsDragonsLivingEntityRenderState extends LivingEntityRenderState implements GeoRenderState {
    private final Map<DataTicket<?>, Object> geckolibData = new IdentityHashMap<>();

    @Override
    public Map<DataTicket<?>, Object> getDataMap() {
        return geckolibData;
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
