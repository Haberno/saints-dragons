package com.leon.saintsdragons.fabric.entity.part;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class FabricPartEntities {
    private static final Identifier DRAGON_PART_ID = SaintsDragonsCommon.rl("dragon_part");
    private static final ResourceKey<EntityType<?>> DRAGON_PART_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, DRAGON_PART_ID);
    public static final EntityType<FabricDragonPart> DRAGON_PART =
            EntityType.Builder.<FabricDragonPart>of(FabricDragonPart::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(0)
                    .updateInterval(1)
                    .noSummon()
                    .build(DRAGON_PART_KEY);

    private FabricPartEntities() {
    }

    public static void register() {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, DRAGON_PART_ID, DRAGON_PART);
    }
}
