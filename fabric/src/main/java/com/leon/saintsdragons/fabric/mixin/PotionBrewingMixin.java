package com.leon.saintsdragons.fabric.mixin;

import com.leon.saintsdragons.common.registry.ModItems;
import com.leon.saintsdragons.common.registry.ModPotionItems;
import com.leon.saintsdragons.common.registry.ModPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public final class PotionBrewingMixin {
    @Unique
    private static boolean isCustomRecipeIngredient(ItemStack ingredient) {
        return ingredient.is(ModItems.VARASUCHUS_SCALE.get()) || ingredient.is(ModItems.IGNIVORUS_TOOTH.get());
    }

    @Unique
    private static boolean isSaintsDragonsPotion(ItemStack stack) {
        return stack.is(ModPotionItems.POTION_OF_TIDEGUARD.get())
                || stack.is(ModPotionItems.POTION_OF_SEARING.get())
                || hasPotion(stack, BuiltInRegistries.POTION.wrapAsHolder(ModPotions.TIDEGUARD.get()))
                || hasPotion(stack, BuiltInRegistries.POTION.wrapAsHolder(ModPotions.SEARING.get()));
    }

    @Unique
    private static boolean hasPotion(ItemStack stack, Holder<Potion> potion) {
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(potion);
    }

    @Unique
    private static void setPotion(ItemStack stack, Potion potion) {
        stack.set(DataComponents.POTION_CONTENTS,
                new PotionContents(BuiltInRegistries.POTION.wrapAsHolder(potion)));
    }

    @Inject(method = "isIngredient", at = @At("HEAD"), cancellable = true)
    private static void saintsdragons$allowCustomIngredients(
            ItemStack itemStack,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (isCustomRecipeIngredient(itemStack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isPotionIngredient", at = @At("HEAD"), cancellable = true)
    private static void saintsdragons$allowCustomPotionIngredients(
            ItemStack itemStack,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (isCustomRecipeIngredient(itemStack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "hasMix", at = @At("HEAD"), cancellable = true)
    private static void saintsdragons$blockAwkwardSplashAndLingering(
            ItemStack itemStack,
            ItemStack itemStack2,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (isSaintsDragonsPotion(itemStack)) {
            cir.setReturnValue(false);
            return;
        }

        if (!isCustomRecipeIngredient(itemStack2)) {
            return;
        }

        if (!hasPotion(itemStack, Potions.AWKWARD)) {
            return;
        }

        if (!itemStack.is(Items.POTION)) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(true);
    }

    @Inject(method = "hasPotionMix", at = @At("HEAD"), cancellable = true)
    private static void saintsdragons$blockVanillaPotionFamilyConversions(
            ItemStack itemStack,
            ItemStack itemStack2,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (isCustomRecipeIngredient(itemStack2)
                && itemStack.is(Items.POTION)
                && hasPotion(itemStack, Potions.AWKWARD)) {
            cir.setReturnValue(true);
            return;
        }

        if (isSaintsDragonsPotion(itemStack)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "mix", at = @At("HEAD"), cancellable = true)
    private static void saintsdragons$customPotionItemOutput(
            ItemStack itemStack,
            ItemStack itemStack2,
            CallbackInfoReturnable<ItemStack> cir
    ) {
        if (isSaintsDragonsPotion(itemStack)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        if (!hasPotion(itemStack, Potions.AWKWARD) || !itemStack.is(Items.POTION)) {
            return;
        }

        if (itemStack2.is(ModItems.VARASUCHUS_SCALE.get())) {
            ItemStack output = new ItemStack(ModPotionItems.POTION_OF_TIDEGUARD.get());
            setPotion(output, ModPotions.TIDEGUARD.get());
            cir.setReturnValue(output);
            return;
        }

        if (itemStack2.is(ModItems.IGNIVORUS_TOOTH.get())) {
            ItemStack output = new ItemStack(ModPotionItems.POTION_OF_SEARING.get());
            setPotion(output, ModPotions.SEARING.get());
            cir.setReturnValue(output);
        }
    }
}
