package com.leon.saintsdragons.common.registry;

import com.leon.saintsdragons.common.item.FixedPotionItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModPotionItems {
    private ModPotionItems() {}

    public static final Supplier<Item> POTION_OF_TIDEGUARD =
            ModItems.register("potion_of_tideguard",
                    properties -> new FixedPotionItem(
                            properties.stacksTo(1),
                            ModPotions.TIDEGUARD
                    ));

    public static final Supplier<Item> POTION_OF_SEARING =
            ModItems.register("potion_of_searing",
                    properties -> new FixedPotionItem(
                            properties.stacksTo(1),
                            ModPotions.SEARING
                    ));

    public static void init() {}
}
