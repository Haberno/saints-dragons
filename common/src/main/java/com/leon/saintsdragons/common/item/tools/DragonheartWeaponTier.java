package com.leon.saintsdragons.common.item.tools;

import com.leon.saintsdragons.common.registry.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public final class DragonheartWeaponTier {
    public static final ToolMaterial CHUNK = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2600,
            5.0F,
            5.0F,
            18,
            ModTags.Items.DRAGONHEART_CHUNK_TOOL_MATERIALS
    );
    public static final ToolMaterial ALLOY = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            3400,
            10.0F,
            7.0F,
            20,
            ModTags.Items.DRAGONHEART_ALLOY_TOOL_MATERIALS
    );

    private DragonheartWeaponTier() {
    }
}
