package com.leon.saintsdragons.common.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public final class ModArmorMaterials {
    public static final ArmorMaterial DRACONIAN_FLESH = new ArmorMaterial(
            25,
            defense(3, 7, 5, 3),
            18,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            1.0F,
            0.0F,
            ItemTags.REPAIRS_LEATHER_ARMOR,
            // Must be our own equipment asset: EquipmentAssets.LEATHER made the worn
            // armour render with vanilla's dyeable leather textures.
            ModEquipmentAssets.DRACONIAN
    );

    public static final ArmorMaterial DRAGONHEART_CHUNK = new ArmorMaterial(
            45,
            defense(4, 9, 7, 4),
            18,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            0.15F,
            ItemTags.REPAIRS_NETHERITE_ARMOR,
            EquipmentAssets.NETHERITE
    );

    public static final ArmorMaterial DRAGONHEART_ALLOY = new ArmorMaterial(
            55,
            defense(5, 10, 8, 5),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            5.0F,
            0.0F,
            ItemTags.REPAIRS_NETHERITE_ARMOR,
            EquipmentAssets.NETHERITE
    );

    private static Map<ArmorType, Integer> defense(int boots, int chestplate, int leggings, int helmet) {
        return Map.of(
                ArmorType.BOOTS, boots,
                ArmorType.CHESTPLATE, chestplate,
                ArmorType.LEGGINGS, leggings,
                ArmorType.HELMET, helmet,
                ArmorType.BODY, 0
        );
    }

    private ModArmorMaterials() {
    }
}
