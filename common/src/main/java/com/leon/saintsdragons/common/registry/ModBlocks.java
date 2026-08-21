package com.leon.saintsdragons.common.registry;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.block.*;
import com.leon.saintsdragons.platform.RegistryHelper;
import com.leon.saintsdragons.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryHelper.RegistryWrapper<Block> REGISTER =
            Services.PLATFORM.getRegistryHelper()
                    .create(Registries.BLOCK, () -> BuiltInRegistries.BLOCK, SaintsDragonsCommon.MOD_ID);

    private static <B extends Block> Supplier<B> register(String name, Function<ResourceKey<Block>, B> factory) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, SaintsDragonsCommon.rl(name));
        return REGISTER.register(name, () -> factory.apply(key));
    }

    public static final Supplier<Block> RAEVYX_EGG =
            register("raevyx_egg",
                    key -> new RaevyxEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_BLACK)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> IGNIVORUS_EGG =
            register("ignivorus_egg",
                    key -> new IgnivorusEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_RED)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> CINDERVANE_EGG =
            register("cindervane_egg",
                    key -> new CindervaneEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_ORANGE)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> VARASUCHUS_EGG =
            register("varasuchus_egg",
                    key -> new VarasuchusEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_BLACK)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> STEGONAUT_EGG =
            register("stegonaut_egg",
                    key -> new StegonautEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.TERRACOTTA_BROWN)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> VOLITANS_EGG =
            register("volitans_egg",
                    key -> new VolitansEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_LIGHT_BLUE)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> ATROXIIA_EGG =
            register("atroxiia_egg",
                    key -> new AtroxiiaEggBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_LIGHT_BLUE)
                            .strength(0.5F)
                            .sound(SoundType.METAL)
                            .noOcclusion()
                            .randomTicks()));

    public static final Supplier<Block> DRACONIAN_PELLUCIDA =
            register("draconian_pellucida",
                    key -> new DraconianPellucidaBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .strength(0.0F)
                            .sound(SoundType.SLIME_BLOCK)
                            .dynamicShape()
                            .noOcclusion()));

    public static final Supplier<Block> DRACONIAN_NUCLEUS =
            register("draconian_nucleus",
                    key -> new DraconianNucleusBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .strength(0.5F)
                            .sound(SoundType.SLIME_BLOCK)
                            .lightLevel(state -> 8)
                            .noOcclusion()));
    public static final Supplier<Block> DRACONIC_CRUCIBLE =
            register("draconic_crucible",
                    key -> new DraconicCrucibleBlock(BlockBehaviour.Properties.of().setId(key)
                            .mapColor(MapColor.COLOR_BLACK)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()
                            .lightLevel(state -> state.getValue(DraconicCrucibleBlock.LIT) ? 13 : 0)
                            .noOcclusion()));
    public static final Supplier<Block> DRAGONHEART_ALLOY_BLOCK =
            register("dragonheart_alloy_block",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).setId(key)
                            .mapColor(MapColor.COLOR_RED)
                            .strength(12.0F, 1200.0F)
                            .requiresCorrectToolForDrops()));
    public static final Supplier<Block> DRAGONHEART_BLOCK =
            register("dragonheart_block",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).setId(key)
                            .mapColor(MapColor.COLOR_RED)
                            .strength(12.0F, 1200.0F)
                            .requiresCorrectToolForDrops()));
    public static final Supplier<Block> DEEPSLATE_WORLDROOT_ORE =
            register("deepslate_worldroot_ore",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).setId(key)
                            .mapColor(MapColor.COLOR_LIGHT_BLUE)
                            .requiresCorrectToolForDrops()));
    public static final Supplier<Block> DRAGONHEART_ORE =
            register("dragonheart_ore",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).setId(key)
                            .mapColor(MapColor.COLOR_YELLOW)
                            .requiresCorrectToolForDrops()));
    public static final Supplier<Block> WORLDROOT_BLOCK =
            register("worldroot_block",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(key)
                            .mapColor(MapColor.COLOR_BROWN)
                            .requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_WORLDROOT_BLOCK =
            register("raw_worldroot_block",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).setId(key)
                            .mapColor(MapColor.COLOR_BROWN)
                            .requiresCorrectToolForDrops()));
    public static final Supplier<Block> IGNIVORUS_INCUBATOR_BLOCK =
            register("ignivorus_incubator_block",
                    key -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).setId(key)
                            .mapColor(MapColor.COLOR_RED)
                            .requiresCorrectToolForDrops()));

    public static void register() {
        REGISTER.register();
    }
}
