package com.leon.saintsdragons.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.leon.saintsdragons.common.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class DraconicCrucibleShapedRecipe implements Recipe<CraftingInput> {
    public static final int GRID_SIZE = 9;

    private final int width;
    private final int height;
    private final List<Optional<Ingredient>> ingredients;
    private final ItemStack result;
    private final int requiredHeatLevel;
    private final int processingTime;
    private final int priority;
    private final List<String> encodedPattern;
    private final Map<String, Ingredient> encodedKey;

    private DraconicCrucibleShapedRecipe(int width, int height,
                                         List<Optional<Ingredient>> ingredients,
                                         ItemStack result, int requiredHeatLevel, int processingTime,
                                         int priority, List<String> encodedPattern,
                                         Map<String, Ingredient> encodedKey) {
        this.width = width;
        this.height = height;
        this.ingredients = List.copyOf(ingredients);
        this.result = result;
        this.requiredHeatLevel = requiredHeatLevel;
        this.processingTime = processingTime;
        this.priority = priority;
        this.encodedPattern = List.copyOf(encodedPattern);
        this.encodedKey = Map.copyOf(encodedKey);
    }

    private static DraconicCrucibleShapedRecipe fromSerialized(List<String> pattern,
                                                                Map<String, Ingredient> key,
                                                                ItemStack result,
                                                                int heat,
                                                                int processingTime,
                                                                int priority) {
        if (pattern.isEmpty() || pattern.size() > 3) {
            throw new IllegalArgumentException("Draconic Crucible patterns must contain between 1 and 3 rows");
        }
        int width = pattern.getFirst().length();
        if (width < 1 || width > 3) {
            throw new IllegalArgumentException("Draconic Crucible pattern width must be between 1 and 3");
        }
        List<Optional<Ingredient>> ingredients = new ArrayList<>(width * pattern.size());
        int occupiedSlots = 0;
        for (String row : pattern) {
            if (row.length() != width) {
                throw new IllegalArgumentException("All Draconic Crucible pattern rows must have the same width");
            }
            for (int column = 0; column < width; column++) {
                char symbol = row.charAt(column);
                if (symbol == ' ') {
                    ingredients.add(Optional.empty());
                    continue;
                }
                Ingredient ingredient = key.get(String.valueOf(symbol));
                if (ingredient == null) {
                    throw new IllegalArgumentException("Pattern references undefined symbol '" + symbol + "'");
                }
                ingredients.add(Optional.of(ingredient));
                occupiedSlots++;
            }
        }
        if (occupiedSlots == 0) {
            throw new IllegalArgumentException("Draconic Crucible patterns must contain at least one ingredient");
        }
        return new DraconicCrucibleShapedRecipe(
                width, pattern.size(), ingredients, result, heat, processingTime, priority, pattern, key);
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        return input.width() == 3 && input.height() == 3 && findMatch(input) != null;
    }

    public boolean consumeInputs(Container container) {
        if (container.getContainerSize() < GRID_SIZE) {
            return false;
        }
        List<ItemStack> stacks = new ArrayList<>(GRID_SIZE);
        for (int slot = 0; slot < GRID_SIZE; slot++) {
            stacks.add(container.getItem(slot));
        }
        Match match = findMatch(CraftingInput.of(3, 3, stacks));
        if (match == null) {
            return false;
        }
        for (int row = 0; row < this.height; row++) {
            for (int column = 0; column < this.width; column++) {
                int ingredientColumn = match.mirrored ? this.width - column - 1 : column;
                if (this.ingredients.get(ingredientColumn + row * this.width).isPresent()) {
                    int slot = match.offsetX + column + (match.offsetY + row) * 3;
                    container.removeItem(slot, 1);
                }
            }
        }
        return true;
    }

    @Nullable
    private Match findMatch(CraftingInput input) {
        for (int offsetY = 0; offsetY <= 3 - this.height; offsetY++) {
            for (int offsetX = 0; offsetX <= 3 - this.width; offsetX++) {
                if (matchesAt(input, offsetX, offsetY, false)) {
                    return new Match(offsetX, offsetY, false);
                }
                if (matchesAt(input, offsetX, offsetY, true)) {
                    return new Match(offsetX, offsetY, true);
                }
            }
        }
        return null;
    }

    private boolean matchesAt(CraftingInput input, int offsetX, int offsetY, boolean mirrored) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                int recipeX = column - offsetX;
                int recipeY = row - offsetY;
                Optional<Ingredient> expected = Optional.empty();
                if (recipeX >= 0 && recipeX < this.width && recipeY >= 0 && recipeY < this.height) {
                    int ingredientX = mirrored ? this.width - recipeX - 1 : recipeX;
                    expected = this.ingredients.get(ingredientX + recipeY * this.width);
                }
                if (!Ingredient.testOptionalIngredient(expected, input.getItem(column, row))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int requiredHeatLevel() { return this.requiredHeatLevel; }
    public int width() { return this.width; }
    public int height() { return this.height; }
    public Optional<Ingredient> ingredientAt(int slot) { return this.ingredients.get(slot); }
    public ItemStack result() { return this.result.copy(); }
    public int processingTime() { return this.processingTime; }
    public int priority() { return this.priority; }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input,
                                       @NotNull HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<CraftingInput>> getSerializer() {
        return ModRecipes.DRACONIC_CRUCIBLE_SHAPED_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<CraftingInput>> getType() {
        return ModRecipes.DRACONIC_CRUCIBLE_SHAPED_TYPE.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(this.ingredients);
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(this.width);
        buffer.writeVarInt(this.height);
        buffer.writeVarInt(this.ingredients.size());
        for (Optional<Ingredient> ingredient : this.ingredients) {
            buffer.writeBoolean(ingredient.isPresent());
            ingredient.ifPresent(value -> Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, value));
        }
        ItemStack.STREAM_CODEC.encode(buffer, this.result);
        buffer.writeVarInt(this.requiredHeatLevel);
        buffer.writeVarInt(this.processingTime);
        buffer.writeInt(this.priority);
    }

    private static DraconicCrucibleShapedRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        int width = buffer.readVarInt();
        int height = buffer.readVarInt();
        int size = buffer.readVarInt();
        List<Optional<Ingredient>> ingredients = new ArrayList<>(size);
        for (int slot = 0; slot < size; slot++) {
            ingredients.add(buffer.readBoolean()
                    ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer))
                    : Optional.empty());
        }
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        int heat = buffer.readVarInt();
        int processingTime = buffer.readVarInt();
        int priority = buffer.readInt();
        EncodedForm encoded = encodeIngredients(width, height, ingredients);
        return new DraconicCrucibleShapedRecipe(
                width, height, ingredients, result, heat, processingTime, priority,
                encoded.pattern(), encoded.key());
    }

    private static EncodedForm encodeIngredients(int width, int height,
                                                  List<Optional<Ingredient>> ingredients) {
        Map<String, Ingredient> key = new LinkedHashMap<>();
        List<String> pattern = new ArrayList<>(height);
        int nextSymbol = 'A';
        for (int row = 0; row < height; row++) {
            StringBuilder encodedRow = new StringBuilder(width);
            for (int column = 0; column < width; column++) {
                Optional<Ingredient> ingredient = ingredients.get(column + row * width);
                if (ingredient.isEmpty()) {
                    encodedRow.append(' ');
                } else {
                    String symbol = String.valueOf((char) nextSymbol++);
                    key.put(symbol, ingredient.orElseThrow());
                    encodedRow.append(symbol);
                }
            }
            pattern.add(encodedRow.toString());
        }
        return new EncodedForm(pattern, key);
    }

    public static final class Serializer implements RecipeSerializer<DraconicCrucibleShapedRecipe> {
        private static final MapCodec<DraconicCrucibleShapedRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.STRING.listOf().fieldOf("pattern").forGetter(recipe -> recipe.encodedPattern),
                        Codec.unboundedMap(Codec.STRING, Ingredient.CODEC).fieldOf("key")
                                .forGetter(recipe -> recipe.encodedKey),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codec.intRange(1, 3).optionalFieldOf("required_heat_level", 1)
                                .forGetter(DraconicCrucibleShapedRecipe::requiredHeatLevel),
                        Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("processing_time", 200)
                                .forGetter(DraconicCrucibleShapedRecipe::processingTime),
                        Codec.INT.optionalFieldOf("priority", 0)
                                .forGetter(DraconicCrucibleShapedRecipe::priority)
                ).apply(instance, DraconicCrucibleShapedRecipe::fromSerialized));
        private static final StreamCodec<RegistryFriendlyByteBuf, DraconicCrucibleShapedRecipe> STREAM_CODEC =
                StreamCodec.ofMember(
                        DraconicCrucibleShapedRecipe::toNetwork,
                        DraconicCrucibleShapedRecipe::fromNetwork
                );

        @Override
        public @NotNull MapCodec<DraconicCrucibleShapedRecipe> codec() { return CODEC; }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, DraconicCrucibleShapedRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    private record EncodedForm(List<String> pattern, Map<String, Ingredient> key) {}
    private record Match(int offsetX, int offsetY, boolean mirrored) {}
}
