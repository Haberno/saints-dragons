package com.leon.saintsdragons.fabric.mixin;

import com.leon.saintsdragons.common.registry.ModItems;
import com.leon.saintsdragons.common.registry.ModPotionItems;
import com.leon.saintsdragons.common.registry.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public final class BrewingStandBlockEntityMixin {
    @Unique
    private static final int BOTTLE_SLOT_START = 0;
    @Unique
    private static final int BOTTLE_SLOT_END = 2;
    @Unique
    private static final int INGREDIENT_SLOT = 3;

    @Unique
    private static boolean hasPotion(ItemStack stack, Holder<Potion> potion) {
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(potion);
    }

    @Unique
    private static void setPotion(ItemStack stack, Potion potion) {
        stack.set(DataComponents.POTION_CONTENTS,
                new PotionContents(BuiltInRegistries.POTION.wrapAsHolder(potion)));
    }

    @Inject(method = "doBrew", at = @At("HEAD"), cancellable = true)
    private static void saintsdragons$brewCustomPotions(
            Level level,
            BlockPos blockPos,
            NonNullList<ItemStack> nonNullList,
            CallbackInfo ci
    ) {
        ItemStack ingredient = nonNullList.get(INGREDIENT_SLOT);
        if (ingredient.isEmpty()) {
            return;
        }

        boolean tideguard = ingredient.is(ModItems.VARASUCHUS_SCALE.get());
        boolean searing = ingredient.is(ModItems.IGNIVORUS_TOOTH.get());
        if (!tideguard && !searing) {
            return;
        }

        boolean brewedAny = false;
        for (int slot = BOTTLE_SLOT_START; slot <= BOTTLE_SLOT_END; slot++) {
            ItemStack input = nonNullList.get(slot);
            if (!input.is(Items.POTION) || !hasPotion(input, Potions.AWKWARD)) {
                continue;
            }

            ItemStack output = new ItemStack(tideguard ? ModPotionItems.POTION_OF_TIDEGUARD.get() : ModPotionItems.POTION_OF_SEARING.get());
            setPotion(output, tideguard ? ModPotions.TIDEGUARD.get() : ModPotions.SEARING.get());
            nonNullList.set(slot, output);
            brewedAny = true;
        }

        if (!brewedAny) {
            return;
        }

        ingredient.shrink(1);
        level.levelEvent(1035, blockPos, 0);
        ci.cancel();
    }
}
