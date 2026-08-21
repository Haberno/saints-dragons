package com.leon.saintsdragons.common.init;

import com.leon.saintsdragons.common.registry.ModItems;
import com.leon.saintsdragons.common.registry.ModPotions;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.core.registries.BuiltInRegistries;

public final class CommonBrewingRecipes {
    private CommonBrewingRecipes() {
    }

    public static void register(PotionBrewing.Builder builder) {
        builder.addMix(Potions.AWKWARD, ModItems.VARASUCHUS_SCALE.get(),
                BuiltInRegistries.POTION.wrapAsHolder(ModPotions.TIDEGUARD.get()));
        builder.addMix(Potions.AWKWARD, ModItems.IGNIVORUS_TOOTH.get(),
                BuiltInRegistries.POTION.wrapAsHolder(ModPotions.SEARING.get()));
    }
}
