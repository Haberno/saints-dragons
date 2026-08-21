package com.leon.saintsdragons.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.leon.saintsdragons.common.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record DraconicCrucibleSmeltingRecipe(
        Ingredient ingredient,
        ItemStack result,
        int requiredHeatLevel,
        int processingTime,
        int priority
) implements Recipe<SingleRecipeInput> {
    @Override
    public boolean matches(@NotNull SingleRecipeInput input, @NotNull Level level) {
        return this.ingredient.test(input.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input,
                                       @NotNull HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return ModRecipes.DRACONIC_CRUCIBLE_SMELTING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipes.DRACONIC_CRUCIBLE_SMELTING_TYPE.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.create(this.ingredient);
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    public ItemStack result() {
        return this.result.copy();
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, this.ingredient);
        ItemStack.STREAM_CODEC.encode(buffer, this.result);
        buffer.writeVarInt(this.requiredHeatLevel);
        buffer.writeVarInt(this.processingTime);
        buffer.writeInt(this.priority);
    }

    private static DraconicCrucibleSmeltingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        return new DraconicCrucibleSmeltingRecipe(
                Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
                ItemStack.STREAM_CODEC.decode(buffer),
                buffer.readVarInt(),
                buffer.readVarInt(),
                buffer.readInt()
        );
    }

    public static final class Serializer implements RecipeSerializer<DraconicCrucibleSmeltingRecipe> {
        private static final MapCodec<DraconicCrucibleSmeltingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("ingredient").forGetter(DraconicCrucibleSmeltingRecipe::ingredient),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codec.intRange(1, 3).optionalFieldOf("required_heat_level", 1)
                                .forGetter(DraconicCrucibleSmeltingRecipe::requiredHeatLevel),
                        Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("processing_time", 200)
                                .forGetter(DraconicCrucibleSmeltingRecipe::processingTime),
                        Codec.INT.optionalFieldOf("priority", 0)
                                .forGetter(DraconicCrucibleSmeltingRecipe::priority)
                ).apply(instance, DraconicCrucibleSmeltingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, DraconicCrucibleSmeltingRecipe> STREAM_CODEC =
                StreamCodec.ofMember(
                        DraconicCrucibleSmeltingRecipe::toNetwork,
                        DraconicCrucibleSmeltingRecipe::fromNetwork
                );

        @Override
        public @NotNull MapCodec<DraconicCrucibleSmeltingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, DraconicCrucibleSmeltingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
