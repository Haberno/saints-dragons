package com.leon.saintsdragons.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class DraconianArmorItem extends Item {
    private final ArmorType type;

    public DraconianArmorItem(ArmorMaterial material, ArmorType type, Properties properties) {
        super(properties.humanoidArmor(material, type));
        this.type = type;
    }

    public ArmorType getType() {
        return type;
    }

    // Forge's armor texture hook also feeds render replacements such as Epic Fight.
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, @Nullable String type) {
        if (type != null) {
            return null;
        }

        return com.leon.saintsdragons.client.renderer.armor.DraconianArmorTextures
                .texture(slot == EquipmentSlot.LEGS)
                .toString();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.saintsdragons.draconian_armor.tooltip.passive.title")
                .withStyle(ChatFormatting.WHITE));
        tooltip.accept(Component.empty()
                .append(Component.translatable("item.saintsdragons.draconian_armor.tooltip.full_set")
                        .withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(" "))
                .append(Component.translatable("item.saintsdragons.draconian_armor.tooltip.passive.description")
                        .withStyle(ChatFormatting.GRAY)));
    }
}
