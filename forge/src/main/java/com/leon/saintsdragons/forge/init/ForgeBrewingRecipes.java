package com.leon.saintsdragons.forge.init;

import com.leon.saintsdragons.common.registry.ModItems;
import com.leon.saintsdragons.common.registry.ModPotionItems;
import com.leon.saintsdragons.common.registry.ModPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.common.crafting.ingredients.StrictNBTIngredient;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;

public final class ForgeBrewingRecipes {
    private static boolean registered;

    private ForgeBrewingRecipes() {
    }

    public static void register() {
        if (!registered) {
            BrewingRecipeRegisterEvent.BUS.addListener(ForgeBrewingRecipes::registerRecipes);
            registered = true;
        }
    }

    private static void registerRecipes(BrewingRecipeRegisterEvent event) {
        ItemStack awkwardPotion = PotionContents.createItemStack(
                Items.POTION, Potions.AWKWARD);
        ItemStack tideguardPotion = PotionContents.createItemStack(
                ModPotionItems.POTION_OF_TIDEGUARD.get(),
                BuiltInRegistries.POTION.wrapAsHolder(ModPotions.TIDEGUARD.get()));
        ItemStack searingPotion = PotionContents.createItemStack(
                ModPotionItems.POTION_OF_SEARING.get(),
                BuiltInRegistries.POTION.wrapAsHolder(ModPotions.SEARING.get()));

        event.addRecipe(
                StrictNBTIngredient.of(awkwardPotion),
                Ingredient.of(ModItems.VARASUCHUS_SCALE.get()),
                tideguardPotion
        );
        event.addRecipe(
                StrictNBTIngredient.of(awkwardPotion),
                Ingredient.of(ModItems.IGNIVORUS_TOOTH.get()),
                searingPotion
        );
    }
}
