package com.leon.saintsdragons.client.compat.jei;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.recipe.DraconicCrucibleShapedRecipe;
import com.leon.saintsdragons.common.recipe.DraconicCrucibleSmeltingRecipe;
import com.leon.saintsdragons.common.registry.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;


@JeiPlugin
public final class SaintsDragonsJeiPlugin implements IModPlugin {
    public static final RecipeType<DraconicCrucibleShapedRecipe> CRUCIBLE_CRAFTING =
            RecipeType.create(SaintsDragonsCommon.MOD_ID, "draconic_crucible",
                    DraconicCrucibleShapedRecipe.class);
    public static final RecipeType<DraconicCrucibleSmeltingRecipe> CRUCIBLE_SMELTING =
            RecipeType.create(SaintsDragonsCommon.MOD_ID, "draconic_crucible_smelting",
                    DraconicCrucibleSmeltingRecipe.class);

    private static final Identifier PLUGIN_ID = SaintsDragonsCommon.rl("jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new DraconicCrucibleCraftingJeiCategory(guiHelper, CRUCIBLE_CRAFTING),
                new DraconicCrucibleSmeltingJeiCategory(guiHelper, CRUCIBLE_SMELTING)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // Custom recipes are no longer synchronized as full recipe objects in 1.21.11.
        // Re-register these from JEI's recipe-data hook once its new API is stabilized.
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.DRACONIC_CRUCIBLE.get(),
                CRUCIBLE_CRAFTING, CRUCIBLE_SMELTING, RecipeTypes.SMELTING);
    }
}
