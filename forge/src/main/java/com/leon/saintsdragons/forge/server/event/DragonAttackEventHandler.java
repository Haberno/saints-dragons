package com.leon.saintsdragons.forge.server.event;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.base.DragonEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SaintsDragonsCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DragonAttackEventHandler {

    @SubscribeEvent
    public static boolean onPlayerAttackEntity(AttackEntityEvent event) {
        if (event == null || event.getEntity() == null) return false;
        if (event.getEntity().level().isClientSide()) return false;
        var player = event.getEntity();
        if (!(player.getVehicle() instanceof DragonEntity dragon)) return false;
        if (dragon.isBaby()) return false;
        if (dragon.areRiderControlsLocked()) return false;
        if (!dragon.isTame() || !dragon.isOwnedBy(player)) return false;
        var abilityType = dragon.getPrimaryAttackAbility();
        if (abilityType == null) return false;

        dragon.combatManager.tryUseAbility(abilityType);
        return true;
    }
}
