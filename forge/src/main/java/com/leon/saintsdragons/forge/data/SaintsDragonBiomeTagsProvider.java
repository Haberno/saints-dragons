package com.leon.saintsdragons.forge.data;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.registry.ConventionalTags;
import com.leon.saintsdragons.common.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class SaintsDragonBiomeTagsProvider extends BiomeTagsProvider {
    private static final Identifier[] CINDERVANE_COMPAT_BIOMES = {
            rl("natures_spirit", "dusty_slopes"),
            rl("natures_spirit", "lively_dunes"),
            rl("natures_spirit", "blooming_dunes"),
            rl("natures_spirit", "stratified_desert"),
            rl("natures_spirit", "woody_highlands"),
            rl("regions_unexplored", "arid_mountains"),
            rl("terralith", "white_mesa"),
            rl("terralith", "warped_mesa"),
            rl("terralith", "painted_mountains"),
            rl("terralith", "bryce_canyon"),
            rl("biomeswevegone", "rugged_badlands"),
            rl("biomeswevegone", "red_rock_valley"),
            rl("biomeswevegone", "red_rock_peaks"),
            rl("natures_spirit", "scorched_dunes"),
            rl("natures_spirit", "drylands"),
            rl("regions_unexplored", "saguaro_desert"),
            rl("regions_unexplored", "joshua_desert"),
            rl("terralith", "ancient_sands"),
            rl("terralith", "lush_desert"),
            rl("terralith", "desert_canyon"),
            rl("biomeswevegone", "windswept_desert"),
            rl("biomeswevegone", "mojave_desert"),
            rl("biomesoplenty", "lush_desert")
    };

    private static final Identifier[] RAEVYX_HOME_COMPAT_BIOMES = {
            rl("natures_spirit", "windswept_sugi_forest"),
            rl("natures_spirit", "sugi_forest"),
            rl("regions_unexplored", "pine_slopes"),
            rl("terralith", "yosemite_lowlands"),
            rl("terralith", "highlands"),
            rl("terralith", "amethyst_canyon"),
            rl("biomesoplenty", "moor"),
            rl("biomesoplenty", "highland")
    };

    private static final Identifier[] RAEVYX_ADDITIONAL_COMPAT_BIOMES = {
            rl("terralith", "gravel_desert"),
            rl("terralith", "volcanic_crater"),
            rl("terralith", "caldera"),
            rl("terralith", "ashen_savanna"),
            rl("biomesoplenty", "wasteland_steppe"),
            rl("biomesoplenty", "wasteland"),
            rl("biomesoplenty", "volcanic_plains"),
            rl("biomesoplenty", "dead_forest")
    };

    private static final Identifier[] STEGONAUT_CAVE_COMPAT_BIOMES = {
            rl("biomeswevegone", "fragment_jungle"),
            rl("biomeswevegone", "crag_gardens"),
            rl("biomesoplenty", "rocky_rainforest"),
            rl("terralith", "white_cliffs"),
            rl("biomeswevegone", "lush_stacks"),
            rl("regions_unexplored", "chalk_cliffs"),
            rl("natures_spirit", "white_cliffs")
    };

    private static final Identifier[] IGNIVORUS_ROOST_COMPAT_BIOMES = {
            rl("regions_unexplored", "ashen_woodland"),
            rl("regions_unexplored", "gravel_beach"),
            rl("terralith", "yellowstone"),
            rl("terralith", "white_cliffs"),
            rl("terralith", "scarlet_mountains"),
            rl("terralith", "gravel_desert"),
            rl("terralith", "gravel_beach"),
            rl("terralith", "granite_cliffs"),
            rl("terralith", "volcanic_peaks"),
            rl("terralith", "volcanic_crater"),
            rl("terralith", "caldera"),
            rl("terralith", "basalt_cliffs"),
            rl("terralith", "ashen_savanna"),
            rl("biomesoplenty", "wasteland_steppe"),
            rl("biomesoplenty", "wasteland"),
            rl("biomesoplenty", "volcano"),
            rl("biomesoplenty", "volcanic_plains"),
            rl("biomesoplenty", "dead_forest"),
            rl("biomesoplenty", "gravel_beach")
    };

    private static final Identifier[] VARASUCHUS_ROOST_COMPAT_BIOMES = {
            rl("natures_spirit", "bamboo_wetlands"),
            rl("natures_spirit", "wisteria_forest"),
            rl("natures_spirit", "marsh"),
            rl("natures_spirit", "tropical_basin"),
            rl("regions_unexplored", "bayou"),
            rl("regions_unexplored", "fen"),
            rl("regions_unexplored", "fungal_fen"),
            rl("regions_unexplored", "marsh"),
            rl("regions_unexplored", "old_growth_bayou"),
            rl("terralith", "orchid_swamp"),
            rl("terralith", "ice_marsh"),
            rl("biomeswevegone", "white_mangrove_marshes"),
            rl("biomeswevegone", "cypress_wetlands"),
            rl("biomeswevegone", "cypress_swamplands"),
            rl("biomeswevegone", "bayou"),
            rl("biomesoplenty", "wetland"),
            rl("biomesoplenty", "marsh"),
            rl("biomesoplenty", "floodplain"),
            rl("biomesoplenty", "bayou"),
            rl("biomesoplenty", "bog")
    };

    public SaintsDragonBiomeTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, SaintsDragonsCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(ModTags.Biomes.HAS_ATROXIIA)
                .addOptionalTag(biomeTag("c", "is_cold/overworld"))
                .addOptionalTag(biomeTag("forge", "is_cold"))
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.ICE_SPIKES);

        var cindervaneBiomes = tag(ModTags.Biomes.HAS_CINDERVANE)
                .addTag(BiomeTags.IS_MOUNTAIN)
                .addTag(BiomeTags.IS_HILL)
                .addTag(BiomeTags.IS_BADLANDS)
                .addOptionalTag(biomeTag("c", "mountain"))
                .addOptionalTag(biomeTag("c", "mountain_peak"))
                .addOptionalTag(biomeTag("c", "mountain_slope"))
                .addOptionalTag(biomeTag("c", "is_mountain"))
                .addOptionalTag(biomeTag("c", "is_mountain/peak"))
                .addOptionalTag(biomeTag("c", "is_mountain/slope"))
                .addOptionalTag(biomeTag("c", "is_hill"))
                .addOptionalTag(biomeTag("c", "is_windswept"))
                .addOptionalTag(biomeTag("c", "badlands"))
                .addOptionalTag(biomeTag("c", "is_badlands"))
                .addOptionalTag(biomeTag("c", "desert"))
                .addOptionalTag(biomeTag("c", "is_desert"))
                .addOptionalTag(biomeTag("forge", "is_mountain"))
                .addOptionalTag(biomeTag("forge", "is_peak"))
                .addOptionalTag(biomeTag("forge", "is_slope"))
                .add(Biomes.STONY_PEAKS)
                .add(Biomes.JAGGED_PEAKS)
                .add(Biomes.FROZEN_PEAKS)
                .add(Biomes.GROVE)
                .add(Biomes.CHERRY_GROVE)
                .add(Biomes.MEADOW)
                .add(Biomes.DESERT);
        for (Identifier biome : CINDERVANE_COMPAT_BIOMES) {
            cindervaneBiomes.addOptional(biome);
        }

        tag(ModTags.Biomes.HAS_IVY_HOUSE)
                .addOptionalTag(biomeTag("c", "forest"))
                .addOptionalTag(biomeTag("c", "is_forest"))
                .addOptionalTag(biomeTag("forge", "is_forest"))
                .add(Biomes.FOREST)
                .add(Biomes.BIRCH_FOREST)
                .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
                .add(Biomes.DARK_FOREST)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.TAIGA)
                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                .add(Biomes.SNOWY_TAIGA);

        tag(ModTags.Biomes.HAS_NULLJAW)
                .add(Biomes.END_BARRENS);

        var raevyxHomeBiomes = tag(ModTags.Biomes.HAS_RAEVYX)
                .add(Biomes.MEADOW)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.CHERRY_GROVE)
                .add(Biomes.WINDSWEPT_FOREST)
                .add(Biomes.GROVE)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.SAVANNA_PLATEAU);
        for (Identifier biome : RAEVYX_HOME_COMPAT_BIOMES) {
            raevyxHomeBiomes.addOptional(biome);
        }
        for (Identifier biome : RAEVYX_ADDITIONAL_COMPAT_BIOMES) {
            raevyxHomeBiomes.addOptional(biome);
        }

        tag(ModTags.Biomes.HAS_STEGONAUT)
                .addTag(BiomeTags.IS_JUNGLE)
                .addOptionalTag(biomeTag("c", "jungle"))
                .addOptionalTag(biomeTag("c", "is_jungle"))
                .addOptionalTag(biomeTag("forge", "is_jungle"))
                .addOptionalTag(biomeTag("c", "plains"))
                .addOptionalTag(biomeTag("c", "is_plains"))
                .addOptionalTag(biomeTag("forge", "is_plains"))
                .add(Biomes.PLAINS)
                .add(Biomes.SUNFLOWER_PLAINS)
                .add(Biomes.JUNGLE)
                .add(Biomes.SPARSE_JUNGLE)
                .add(Biomes.FLOWER_FOREST);

        var stegonautCaveBiomes = tag(ModTags.Biomes.HAS_STEGONAUT_CAVES)
                .addTag(ModTags.Biomes.HAS_STEGONAUT)
                .addOptionalTag(biomeTag("forge", "is_lush"))
                .add(Biomes.LUSH_CAVES);
        for (Identifier biome : STEGONAUT_CAVE_COMPAT_BIOMES) {
            stegonautCaveBiomes.addOptional(biome);
        }

        tag(ModTags.Biomes.HAS_VOLITANS)
                .addOptionalTag(biomeTag("c", "ocean"))
                .addOptionalTag(biomeTag("c", "is_ocean"))
                .addOptionalTag(biomeTag("forge", "is_ocean"))
                .add(Biomes.OCEAN)
                .add(Biomes.DEEP_OCEAN)
                .add(Biomes.COLD_OCEAN)
                .add(Biomes.DEEP_COLD_OCEAN)
                .add(Biomes.FROZEN_OCEAN)
                .add(Biomes.DEEP_FROZEN_OCEAN)
                .add(Biomes.LUKEWARM_OCEAN)
                .add(Biomes.DEEP_LUKEWARM_OCEAN)
                .add(Biomes.WARM_OCEAN);

        tag(ModTags.Biomes.HAS_VOLITANS_FALLBACK)
                .addOptionalTag(biomeTag("c", "beach"))
                .addOptionalTag(biomeTag("forge", "is_swamp"))
                .addOptionalTag(biomeTag("forge", "is_plains"))
                .addOptionalTag(biomeTag("c", "plains"))
                .add(Biomes.BEACH)
                .add(Biomes.STONY_SHORE);

        tag(ModTags.Biomes.HAS_MOOP)
                .addOptionalTag(biomeTag("c", "river"))
                .addOptionalTag(biomeTag("c", "is_river"))
                .addOptionalTag(biomeTag("c", "ocean"))
                .addOptionalTag(biomeTag("c", "is_ocean"))
                .addOptionalTag(biomeTag("forge", "is_river"))
                .addOptionalTag(biomeTag("forge", "is_ocean"))
                .add(Biomes.RIVER)
                .add(Biomes.FROZEN_RIVER)
                .add(Biomes.OCEAN)
                .add(Biomes.DEEP_OCEAN)
                .add(Biomes.COLD_OCEAN)
                .add(Biomes.DEEP_COLD_OCEAN)
                .add(Biomes.FROZEN_OCEAN)
                .add(Biomes.DEEP_FROZEN_OCEAN)
                .add(Biomes.LUKEWARM_OCEAN)
                .add(Biomes.DEEP_LUKEWARM_OCEAN)
                .add(Biomes.WARM_OCEAN)
                .add(Biomes.SWAMP)
                .add(Biomes.MANGROVE_SWAMP);

        tag(ModTags.Biomes.HAS_MOSSBACK)
                .addOptionalTag(biomeTag("c", "jungle"))
                .addOptionalTag(biomeTag("c", "is_jungle"))
                .addOptionalTag(biomeTag("forge", "is_jungle"))
                .add(Biomes.JUNGLE)
                .add(Biomes.SPARSE_JUNGLE)
                .add(Biomes.BAMBOO_JUNGLE);

        var ignivorusRoostBiomes = tag(ModTags.Biomes.HAS_IGNIVORUS_ROOST)
                .addOptionalTag(biomeTag("c", "wasteland"))
                .addOptionalTag(biomeTag("c", "is_wasteland"))
                .addOptionalTag(biomeTag("forge", "is_wasteland"))
                .addOptionalTag(biomeTag("terralith", "volcanic"))
                .add(Biomes.PLAINS)
                .add(Biomes.SAVANNA)
                .add(Biomes.MEADOW)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
                .add(Biomes.WINDSWEPT_FOREST)
                .add(Biomes.DESERT);
        for (Identifier biome : IGNIVORUS_ROOST_COMPAT_BIOMES) {
            ignivorusRoostBiomes.addOptional(biome);
        }

        var varasuchusRoostBiomes = tag(ModTags.Biomes.HAS_VARASUCHUS_ROOST)
                .addTag(BiomeTags.IS_BEACH)
                .addOptionalTag(biomeTag("c", "beach"))
                .addOptionalTag(biomeTag("c", "is_beach"))
                .addOptionalTag(biomeTag("c", "swamp"))
                .addOptionalTag(biomeTag("c", "is_swamp"))
                .addOptionalTag(biomeTag("forge", "is_beach"))
                .addOptionalTag(biomeTag("forge", "is_swamp"))
                .add(Biomes.BEACH)
                .add(Biomes.STONY_SHORE)
                .add(Biomes.SWAMP)
                .add(Biomes.MANGROVE_SWAMP)
                .addOptional(rl("terralith", "gravel_beach"));
        for (Identifier biome : VARASUCHUS_ROOST_COMPAT_BIOMES) {
            varasuchusRoostBiomes.addOptional(biome);
        }

        tag(ModTags.Biomes.HAS_DRAGONHEART_ORE)
                .add(Biomes.END_BARRENS)
                .add(Biomes.END_MIDLANDS)
                .add(Biomes.END_HIGHLANDS);

        tag(ConventionalTags.Biomes.JUNGLE)
                .add(Biomes.JUNGLE)
                .add(Biomes.SPARSE_JUNGLE)
                .add(Biomes.BAMBOO_JUNGLE);
        tag(ConventionalTags.Biomes.IS_JUNGLE)
                .addTag(ConventionalTags.Biomes.JUNGLE);
    }

    private static Identifier rl(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    private static net.minecraft.tags.TagKey<net.minecraft.world.level.biome.Biome> biomeTag(
            String namespace,
            String path
    ) {
        return net.minecraft.tags.TagKey.create(
                net.minecraft.core.registries.Registries.BIOME,
                rl(namespace, path));
    }
}
