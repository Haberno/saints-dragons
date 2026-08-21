package com.leon.saintsdragons.client.renderer;

import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.constant.dataticket.DataTicket;

public final class GeoRenderDataTickets {
    public static final DataTicket<Entity> ANIMATABLE =
            DataTicket.create("saintsdragons_animatable", Entity.class);

    private GeoRenderDataTickets() {
    }
}
