package com.leon.saintsdragons.client.renderer;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.constant.dataticket.DataTicket;

public final class GeoRenderDataTickets {
    public static final DataTicket<Entity> ANIMATABLE =
            DataTicket.create("saintsdragons_animatable", Entity.class);
    public static final DataTicket<LivingEntity> ARMOR_WEARER =
            DataTicket.create("saintsdragons_armor_wearer", LivingEntity.class);

    private GeoRenderDataTickets() {
    }
}
