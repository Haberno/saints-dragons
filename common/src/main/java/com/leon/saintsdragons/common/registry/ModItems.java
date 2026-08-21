package com.leon.saintsdragons.common.registry;

import com.leon.saintsdragons.common.item.*;
import com.leon.saintsdragons.common.item.dragonfood.HeartyDragonMealItem;
import com.leon.saintsdragons.common.item.tools.DragonheartWeaponTier;
import com.leon.saintsdragons.common.item.tools.ConfiguredWorldrootItems;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.platform.RegistryHelper;
import com.leon.saintsdragons.platform.Services;
import com.leon.saintsdragons.server.entity.dragons.Mossback;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    public static final RegistryHelper.RegistryWrapper<Item> REGISTER =
            Services.PLATFORM.getRegistryHelper()
                    .create(Registries.ITEM, () -> BuiltInRegistries.ITEM, SaintsDragonsCommon.MOD_ID);

    public static <I extends Item> Supplier<I> register(String name, Function<Item.Properties, I> factory) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, SaintsDragonsCommon.rl(name));
        return REGISTER.register(name, () -> factory.apply(new Item.Properties().setId(key)));
    }

    public static final Supplier<Item> RAEVYX_SPAWN_EGG =
            register("raevyx_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.RAEVYX,
                            0x000000, 0x8B0000,
                            properties
                    ));

    public static final Supplier<Item> STEGONAUT_SPAWN_EGG =
            register("stegonaut_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.STEGONAUT,
                            0x9E8B70, 0x7148AC,
                            properties
                    ));

    public static final Supplier<Item> CINDERVANE_SPAWN_EGG =
            register("cindervane_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.CINDERVANE,
                            0xF88017, 0x414F49,
                            properties
                    ));

    public static final Supplier<Item> VARASUCHUS_SPAWN_EGG =
            register("varasuchus_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.VARASUCHUS,
                            0x849B59, 0xE8CE74,
                            properties
                    ));

    public static final Supplier<Item> IGNIVORUS_SPAWN_EGG =
            register("ignivorus_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.IGNIVORUS,
                            0x0A0A0A, 0x5A5A5A,
                            properties
                    ));

    public static final Supplier<Item> VOLITANS_SPAWN_EGG =
            register("volitans_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.VOLITANS,
                            0x2E6B7A, 0x9AD0D9,
                            properties
                    ));

    public static final Supplier<Item> NULLJAW_SPAWN_EGG =
            register("nulljaw_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.NULLJAW,
                            0x121118, 0x7E8BA6,
                            properties
                    ));
    public static final Supplier<Item> ATROXIIA_SPAWN_EGG =
            register("atroxiia_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.ATROXIIA,
                            0xFFFFFF, 0x808080,
                            properties
                    ));

    public static final Supplier<Item> MOOP_SPAWN_EGG =
            register("moop_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.MOOP,
                            0x8CC7C8, 0xF2E5B8,
                            properties
                    ));

    public static final Supplier<Item> MOSSBACK_SPAWN_EGG =
            register("mossback_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.MOSSBACK,
                            0x4F6F3A, 0xB7C46A,
                            properties
                    ));

    public static final Supplier<Item> LATCHER_SPAWN_EGG =
            register("latcher_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.LATCHER,
                            0x000000, 0xFFFFFF,
                            properties
                    ));

    public static final Supplier<Item> WINGED_SPAWN_EGG =
            register("winged_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.WINGED,
                            0x000000, 0xFFFFFF,
                            properties
                    ));

    public static final Supplier<Item> WHETTLED_SPAWN_EGG =
            register("whettled_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.WHETTLED,
                            0x000000, 0xFFFFFF,
                            properties
                    ));

    public static final Supplier<Item> DRACONIAN_SWARM_SPAWN_EGG =
            register("draconian_swarm_spawn_egg",
                    properties -> Services.PLATFORM.createDraconianSwarmSpawnEgg(
                            ModEntities.LATCHER,
                            0x000000, 0xFFFFFF,
                            properties
                    ));

    public static final Supplier<Item> DRACONIAN_FLESH =
            register("draconian_flesh", properties -> new Item(properties));

    public static final Supplier<Item> DRAGON_SEAL_STONE =
            register("dragon_seal_stone", properties -> new Item(properties));

    public static final Supplier<Item> DRAGON_BINDER_CORE =
            register("dragon_binder_core", properties -> new Item(properties));

    public static final Supplier<Item> RAW_WORLDROOT =
            register("raw_worldroot", properties -> new Item(properties));

    public static final Supplier<Item> DRAGONHEART_CHUNK =
            register("dragonheart_chunk", properties -> new Item(properties));

    public static final Supplier<Item> DRAGONHEART_ALLOY =
            register("dragonheart_alloy", properties -> new Item(properties));

    public static final Supplier<Item> WORLDROOT_INGOT =
            register("worldroot_ingot", properties -> new Item(properties));

    public static final Supplier<Item> WORLDROOT_SWORD =
            register("worldroot_sword",
                    properties -> new ConfiguredWorldrootItems.Sword(properties));

    public static final Supplier<Item> BLOOD_TEMPEST_KATANA =
            register("blood_tempest_katana",
                    properties -> Services.PLATFORM.createDragonheartSword(
                            DragonheartWeaponTier.CHUNK, 3, -1.0F, 5.0D, 0.8F,
                            properties.rarity(Rarity.RARE)));

    public static final Supplier<Item> DRAGONLORD_SWORD =
            register("dragonlord_sword",
                    properties -> Services.PLATFORM.createDragonheartSword(
                            DragonheartWeaponTier.ALLOY, 5, -2.6F, 7.0D, 0.0F,
                            properties.rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> WORLDROOT_PICKAXE =
            register("worldroot_pickaxe",
                    properties -> new ConfiguredWorldrootItems.Pickaxe(properties));

    public static final Supplier<Item> WORLDROOT_AXE =
            register("worldroot_axe",
                    properties -> new ConfiguredWorldrootItems.Axe(properties));

    public static final Supplier<Item> WORLDROOT_SHOVEL =
            register("worldroot_shovel",
                    properties -> new ConfiguredWorldrootItems.Shovel(properties));

    public static final Supplier<Item> WORLDROOT_HOE =
            register("worldroot_hoe",
                    properties -> new ConfiguredWorldrootItems.Hoe(properties));

    public static final Supplier<Item> IVY_THE_MERCHANT_SPAWN_EGG =
            register("ivy_the_merchant_spawn_egg",
                    properties -> Services.PLATFORM.createSpawnEgg(
                            ModEntities.IVY_THE_DRAGON_MERCHANT,
                            0x6B5B4B, 0xC2A27A,
                            properties
                    ));

    public static final Supplier<Item> IVY_OCTOPUS_PLUSHIE =
            register("ivy_octopus_plushie",
                    properties -> new IvyOctopusPlushieItem(
                            properties
                                    .stacksTo(1)
                                    .rarity(Rarity.RARE)
                    ));

    // Block Items
    public static final Supplier<Item> DRACONIAN_PELLUCIDA =
            register("draconian_pellucida",
                    properties -> new BlockItem(ModBlocks.DRACONIAN_PELLUCIDA.get(), properties));

    public static final Supplier<Item> DRACONIAN_NUCLEUS =
            register("draconian_nucleus",
                    properties -> new BlockItem(ModBlocks.DRACONIAN_NUCLEUS.get(), properties));

    public static final Supplier<Item> DRACONIC_CRUCIBLE =
            register("draconic_crucible",
                    properties -> new BlockItem(ModBlocks.DRACONIC_CRUCIBLE.get(), properties));

    public static final Supplier<Item> DRAGONHEART_ORE =
            register("dragonheart_ore",
                    properties -> new BlockItem(ModBlocks.DRAGONHEART_ORE.get(), properties));

    public static final Supplier<Item> DRAGONHEART_ALLOY_BLOCK =
            register("dragonheart_alloy_block",
                    properties -> new BlockItem(ModBlocks.DRAGONHEART_ALLOY_BLOCK.get(), properties));

    public static final Supplier<Item> DRAGONHEART_BLOCK =
            register("dragonheart_block",
                    properties -> new BlockItem(ModBlocks.DRAGONHEART_BLOCK.get(), properties));

    public static final Supplier<Item> DEEPSLATE_WORLDROOT_ORE =
            register("deepslate_worldroot_ore",
                    properties -> new BlockItem(ModBlocks.DEEPSLATE_WORLDROOT_ORE.get(), properties));

    public static final Supplier<Item> WORLDROOT_BLOCK =
            register("worldroot_block",
                    properties -> new BlockItem(ModBlocks.WORLDROOT_BLOCK.get(), properties));

    public static final Supplier<Item> RAW_WORLDROOT_BLOCK =
            register("raw_worldroot_block",
                    properties -> new BlockItem(ModBlocks.RAW_WORLDROOT_BLOCK.get(), properties));

    public static final Supplier<Item> IGNIVORUS_INCUBATOR_BLOCK =
            register("ignivorus_incubator_block",
                    properties -> new BlockItem(ModBlocks.IGNIVORUS_INCUBATOR_BLOCK.get(), properties));

    public static final Supplier<Item> RAEVYX_EGG =
            register("raevyx_egg",
                    properties -> new BlockItem(ModBlocks.RAEVYX_EGG.get(),
                            properties));

    public static final Supplier<Item> IGNIVORUS_EGG =
            register("ignivorus_egg",
                    properties -> new BlockItem(ModBlocks.IGNIVORUS_EGG.get(),
                            properties));

    public static final Supplier<Item> CINDERVANE_EGG =
            register("cindervane_egg",
                    properties -> new BlockItem(ModBlocks.CINDERVANE_EGG.get(),
                            properties));

    public static final Supplier<Item> VARASUCHUS_EGG =
            register("varasuchus_egg",
                    properties -> new BlockItem(ModBlocks.VARASUCHUS_EGG.get(),
                            properties));

    public static final Supplier<Item> STEGONAUT_EGG =
            register("stegonaut_egg",
                    properties -> new BlockItem(ModBlocks.STEGONAUT_EGG.get(),
                            properties));

    public static final Supplier<Item> VOLITANS_EGG =
            register("volitans_egg",
                    properties -> new BlockItem(ModBlocks.VOLITANS_EGG.get(),
                            properties));

    public static final Supplier<Item> ATROXIIA_EGG =
            register("atroxiia_egg",
                    properties -> new BlockItem(ModBlocks.ATROXIIA_EGG.get(),
                            properties));

    public static final Supplier<Item> DRACONIC_CODEX =
            register("draconic_codex",
                    properties -> new DragonAllyBookItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    //it's unused but important
    public static final Supplier<Item> DRAGON_ENCOUNTER_ICON =
            register("dragon_encounter_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> DRACONIAN_NUCLEUS_PARTICLE_ICON =
            register("draconian_nucleus_particle_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> DRAGON_BINDER_ICON =
            register("dragon_binder_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> DRAGON_SCALE_ICON =
            register("dragon_scale_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> WATER_SPLASH_ICON =
            register("water_splash_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> BLOOD_TEMPEST_ARMOR_SET_ICON =
            register("blood_tempest_armor_set_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> DRAGONLORD_ARMOR_SET_ICON =
            register("dragonlord_armor_set_icon",
                    properties -> new Item(properties));

    public static final Supplier<Item> DRAGON_BRUSH =
            register("dragon_brush",
                    properties -> new DragonBrushItem(
                            properties
                                    .stacksTo(1)
                                    .durability(256)
                    ));
    //end

    public static final Supplier<Item> GOLDEN_DRAGON_BRUSH =
            register("golden_dragon_brush",
                    properties -> new DragonBrushItem(
                            properties
                                    .stacksTo(1)
                                    .durability(256)
                    ));

    public static final Supplier<Item> SCALE_PLUCKER =
            register("scale_plucker",
                    properties -> new Item(
                            properties
                                    .stacksTo(1)
                                    .durability(64)
                    ));

    public static final Supplier<Item> RAW_MOOP =
            register("raw_moop",
                    properties -> new Item(
                            properties
                                    .food(new FoodProperties.Builder()
                                            .nutrition(2)
                                            .saturationModifier(0.1F)
                                            .build())
                    ));

    public static final Supplier<Item> COOKED_MOOP =
            register("cooked_moop",
                    properties -> new Item(
                            properties
                                    .food(new FoodProperties.Builder()
                                            .nutrition(5)
                                            .saturationModifier(0.6F)
                                            .build())
                    ));

    public static final Supplier<Item> BUCKET_OF_MOOP =
            register("bucket_of_moop",
                    properties -> Services.PLATFORM.createMobBucket(
                            ModEntities.MOOP,
                            Fluids.WATER,
                            SoundEvents.BUCKET_EMPTY_FISH,
                            properties
                                    .stacksTo(1)
                    ));

    public static final Supplier<Item> MOSSBACK =
            register("mossback",
                    properties -> new MossbackItem(
                            properties
                                    .stacksTo(16)
                    ));

    public static final Supplier<Item> RAW_MOSSBACK =
            register("raw_mossback",
                    properties -> new Item(
                            properties
                                    .food(new Builder()
                                            .nutrition(4)
                                            .saturationModifier(0.3F)
                                            .build(),
                                            Consumable.builder()
                                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                                            Mossback.createToxinEffect(MobEffects.POISON), 1.0F))
                                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                                            Mossback.createToxinEffect(MobEffects.NAUSEA), 1.0F))
                                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                                            Mossback.createToxinEffect(MobEffects.BLINDNESS), 1.0F))
                                                    .build())
                    ));

    public static final Supplier<Item> COOKED_MOSSBACK =
            register("cooked_mossback",
                    properties -> new Item(
                            properties
                                    .food(new FoodProperties.Builder()
                                            .nutrition(8)
                                            .saturationModifier(0.5F)
                                            .build())
                    ));

    public static final Supplier<Item> RAEVYX_SCALE =
            register("raevyx_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> RAEVYX_WING_HIDE =
            register("raevyx_wing_hide",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> RAEVYX_WINGTALON =
            register("raevyx_wingtalon",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> CINDERVANE_SCALE =
            register("cindervane_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> SEARING_COAL =
            register("searing_coal",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> ANCIENT_DRAGONITE_FRAGMENT =
            register("ancient_dragonite_fragment",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> IGNIVORUS_SCALE =
            register("ignivorus_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> IGNIVORUS_WING_HIDE =
            register("ignivorus_wing_hide",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> IGNIVORUS_HEART =
            register("ignivorus_heart",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> IGNIVORUS_TOOTH =
            register("ignivorus_tooth",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> VARASUCHUS_SCALE =
            register("varasuchus_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> VOLITANS_SCALE =
            register("volitans_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> VOLITANS_SPINE =
            register("volitans_spine",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> ARROW_OF_VENOM =
            register("arrow_of_venom",
                    properties -> new ArrowOfVenomItem(
                            properties
                    ));

    public static final Supplier<Item> STEGONAUT_SCALE =
            register("stegonaut_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> ATROXIIA_SCALE =
            register("atroxiia_scale",
                    properties -> new Item(
                            properties
                    ));

    public static final Supplier<Item> STEGONAUT_BINDER =
            register("stegonaut_binder",
                    properties -> new StegonautBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> ATROXIIA_BINDER =
            register("atroxiia_binder",
                    properties -> new AtroxiiaBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> RAEVYX_BINDER =
            register("raevyx_binder",
                    properties -> new RaevyxBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> CINDERVANE_BINDER =
            register("cindervane_binder",
                    properties -> new CindervaneBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> VARASUCHUS_BINDER =
            register("varasuchus_binder",
                    properties -> new VarasuchusBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));
    public static final Supplier<Item> IGNIVORUS_BINDER =
            register("ignivorus_binder",
                    properties -> new IgnivorusBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));
    public static final Supplier<Item> VOLITANS_BINDER =
            register("volitans_binder",
                    properties -> new VolitansBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> NULLJAW_BINDER =
            register("nulljaw_binder",
                    properties -> new NulljawBinderItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> HEARTY_DRAGON_MEAL =
            register("hearty_dragon_meal",
                    properties -> new HeartyDragonMealItem(
                            properties
                                    .stacksTo(16)
                                    .food(new FoodProperties.Builder()
                                            .nutrition(10)
                                            .saturationModifier(1.2f)
                                            .build())
                    ));
    public static final Supplier<Item> DRACONIAN_CONTROLLER =
            register("draconian_controller",
                    properties -> new DraconianControllerItem(
                            properties
                                    .stacksTo(1)
                                    .durability(0)
                    ));

    public static final Supplier<Item> BLEEDING_BOLT_MUSIC_DISC =
            register("bleeding_bolt_music_disc",
                    properties -> new Item(properties
                            .stacksTo(1)
                            .rarity(Rarity.RARE)
                            .jukeboxPlayable(ResourceKey.create(
                                    Registries.JUKEBOX_SONG,
                                    SaintsDragonsCommon.rl("bleeding_bolt")))));

    public static boolean isDragonBrush(ItemStack stack) {
        return stack.is(ModTags.Items.DRAGON_BRUSHES);
    }

    public static boolean isScalePlucker(ItemStack stack) {
        return stack.is(SCALE_PLUCKER.get());
    }

    public static void register() {
        ModArmors.init();
        ModPotionItems.init();
        REGISTER.register();
    }
}
