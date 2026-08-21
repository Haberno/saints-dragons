package com.leon.saintsdragons.common.registry;

import com.leon.saintsdragons.common.item.DragonlordArmorItem;
import com.leon.saintsdragons.common.item.BloodTempestArmorItem;
import com.leon.saintsdragons.common.item.DraconianArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Supplier;

public final class ModArmors {
    private ModArmors() {}

    public static final Supplier<Item> DRACONIAN_HELMET =
            ModItems.register("draconian_helmet",
                    properties -> new DraconianArmorItem(
                            ModArmorMaterials.DRACONIAN_FLESH,
                            ArmorType.HELMET,
                            properties
                    ));

    public static final Supplier<Item> DRACONIAN_CHESTPLATE =
            ModItems.register("draconian_chestplate",
                    properties -> new DraconianArmorItem(
                            ModArmorMaterials.DRACONIAN_FLESH,
                            ArmorType.CHESTPLATE,
                            properties
                    ));

    public static final Supplier<Item> DRACONIAN_LEGGINGS =
            ModItems.register("draconian_leggings",
                    properties -> new DraconianArmorItem(
                            ModArmorMaterials.DRACONIAN_FLESH,
                            ArmorType.LEGGINGS,
                            properties
                    ));

    public static final Supplier<Item> DRACONIAN_BOOTS =
            ModItems.register("draconian_boots",
                    properties -> new DraconianArmorItem(
                            ModArmorMaterials.DRACONIAN_FLESH,
                            ArmorType.BOOTS,
                            properties
                    ));

    public static final Supplier<Item> BLOOD_TEMPEST_HELMET =
            ModItems.register("blood_tempest_helmet",
                    properties -> new BloodTempestArmorItem(
                            ModArmorMaterials.DRAGONHEART_CHUNK,
                            ArmorType.HELMET,
                            properties.rarity(Rarity.EPIC)
                    ));

    public static final Supplier<Item> BLOOD_TEMPEST_CHESTPLATE =
            ModItems.register("blood_tempest_chestplate",
                    properties -> new BloodTempestArmorItem(
                            ModArmorMaterials.DRAGONHEART_CHUNK,
                            ArmorType.CHESTPLATE,
                            properties.rarity(Rarity.EPIC)
                    ));

    public static final Supplier<Item> BLOOD_TEMPEST_LEGGINGS =
            ModItems.register("blood_tempest_leggings",
                    properties -> new BloodTempestArmorItem(
                            ModArmorMaterials.DRAGONHEART_CHUNK,
                            ArmorType.LEGGINGS,
                            properties.rarity(Rarity.EPIC)
                    ));

    public static final Supplier<Item> BLOOD_TEMPEST_BOOTS =
            ModItems.register("blood_tempest_boots",
                    properties -> new BloodTempestArmorItem(
                            ModArmorMaterials.DRAGONHEART_CHUNK,
                            ArmorType.BOOTS,
                            properties.rarity(Rarity.EPIC)
                    ));

    public static final Supplier<Item> DRAGONLORD_HELMET =
            ModItems.register("dragonlord_helmet",
                    properties -> new DragonlordArmorItem(ModArmorMaterials.DRAGONHEART_ALLOY, ArmorType.HELMET,
                            properties.rarity(Rarity.EPIC)));

    public static final Supplier<Item> DRAGONLORD_CHESTPLATE =
            ModItems.register("dragonlord_chestplate",
                    properties -> new DragonlordArmorItem(ModArmorMaterials.DRAGONHEART_ALLOY, ArmorType.CHESTPLATE,
                            properties.rarity(Rarity.EPIC)));

    public static final Supplier<Item> DRAGONLORD_LEGGINGS =
            ModItems.register("dragonlord_leggings",
                    properties -> new DragonlordArmorItem(ModArmorMaterials.DRAGONHEART_ALLOY, ArmorType.LEGGINGS,
                            properties.rarity(Rarity.EPIC)));

    public static final Supplier<Item> DRAGONLORD_BOOTS =
            ModItems.register("dragonlord_boots",
                    properties -> new DragonlordArmorItem(ModArmorMaterials.DRAGONHEART_ALLOY, ArmorType.BOOTS,
                            properties.rarity(Rarity.EPIC)));

    public static void init() {}
}
