package com.leon.saintsdragons.common.item.tools;

import com.leon.saintsdragons.common.registry.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public final class WorldrootTier {
    public static final ToolMaterial INSTANCE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1950,
            8.8F,
            3.8F,
            14,
            ModTags.Items.WORLDROOT_TOOL_MATERIALS
    );

    private WorldrootTier() {
    }
}
