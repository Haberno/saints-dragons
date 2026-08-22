package com.leon.saintsdragons.common.registry;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

/**
 * Equipment asset keys for armour that is rendered by the vanilla humanoid armour
 * layer. The key selects {@code assets/saintsdragons/equipment/<path>.json}, which in
 * turn names the textures under {@code textures/entity/equipment/<layer>/}.
 *
 * <p>Armour rendered by GeckoLib (Blood Tempest, Dragonlord) never reaches this
 * system, so it does not need a key here.
 */
public final class ModEquipmentAssets {
    public static final ResourceKey<EquipmentAsset> DRACONIAN =
            ResourceKey.create(EquipmentAssets.ROOT_ID, SaintsDragonsCommon.rl("draconian"));

    private ModEquipmentAssets() {
    }
}
