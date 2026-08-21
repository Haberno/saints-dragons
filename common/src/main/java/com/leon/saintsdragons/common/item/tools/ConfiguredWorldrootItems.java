package com.leon.saintsdragons.common.item.tools;

import com.leon.saintsdragons.common.config.ToolsArmorConfig;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

public final class ConfiguredWorldrootItems {
    public static final class Sword extends Item {
        public Sword(Item.Properties properties) {
            super(properties.sword(WorldrootTier.INSTANCE,
                    damageModifier(ToolsArmorConfig.WORLDROOT_SWORD_DAMAGE.get()),
                    speedModifier(ToolsArmorConfig.WORLDROOT_SWORD_SPEED.get())));
        }
    }

    public static final class Pickaxe extends Item {
        public Pickaxe(Item.Properties properties) {
            super(properties.pickaxe(WorldrootTier.INSTANCE,
                    damageModifier(ToolsArmorConfig.WORLDROOT_PICKAXE_DAMAGE.get()),
                    speedModifier(ToolsArmorConfig.WORLDROOT_PICKAXE_SPEED.get())));
        }
    }

    public static final class Axe extends AxeItem {
        public Axe(Item.Properties properties) {
            super(WorldrootTier.INSTANCE,
                    damageModifier(ToolsArmorConfig.WORLDROOT_AXE_DAMAGE.get()),
                    speedModifier(ToolsArmorConfig.WORLDROOT_AXE_SPEED.get()), properties);
        }
    }

    public static final class Shovel extends ShovelItem {
        public Shovel(Item.Properties properties) {
            super(WorldrootTier.INSTANCE,
                    damageModifier(ToolsArmorConfig.WORLDROOT_SHOVEL_DAMAGE.get()),
                    speedModifier(ToolsArmorConfig.WORLDROOT_SHOVEL_SPEED.get()), properties);
        }
    }

    public static final class Hoe extends HoeItem {
        public Hoe(Item.Properties properties) {
            super(WorldrootTier.INSTANCE,
                    damageModifier(ToolsArmorConfig.WORLDROOT_HOE_DAMAGE.get()),
                    speedModifier(ToolsArmorConfig.WORLDROOT_HOE_SPEED.get()), properties);
        }
    }

    private static float damageModifier(double configuredDamage) {
        return (float) (configuredDamage - 1.0D - WorldrootTier.INSTANCE.attackDamageBonus());
    }

    private static float speedModifier(double configuredSpeed) {
        return (float) (configuredSpeed - 4.0D);
    }

    private ConfiguredWorldrootItems() {
    }
}
