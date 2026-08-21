package com.leon.saintsdragons.forge.loot;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.mojang.serialization.MapCodec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SaintsDragonsCommon.MOD_ID);

    public static final RegistryObject<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIERS.register("add_item", () -> AddItemLootModifier.CODEC);

    public static final RegistryObject<MapCodec<? extends IGlobalLootModifier>> DRAGON_CHEST_LOOT =
            LOOT_MODIFIERS.register("dragon_chest_loot", () -> DragonChestLootModifier.CODEC);

    public static void register(BusGroup eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}
