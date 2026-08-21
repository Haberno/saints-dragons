package com.leon.saintsdragons.common.item;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;

public final class ConfiguredItemAttributes {
    public static ItemAttributeModifiers weapon(double attackDamage, double attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage - 1.0D,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed - 4.0D,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public static ItemAttributeModifiers armor(ArmorType type, double armor, double toughness,
                                               double knockbackResistance) {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
        String slotName = type.getName();
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR,
                        modifier("configured_armor_" + slotName, armor, AttributeModifier.Operation.ADD_VALUE),
                        slot)
                .add(Attributes.ARMOR_TOUGHNESS,
                        modifier("configured_armor_toughness_" + slotName, toughness,
                                AttributeModifier.Operation.ADD_VALUE),
                        slot);
        if (knockbackResistance != 0.0D) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE,
                    modifier("configured_knockback_resistance_" + slotName, knockbackResistance,
                            AttributeModifier.Operation.ADD_VALUE),
                    slot);
        }
        return builder.build();
    }

    public static AttributeModifier modifier(String path, double amount, AttributeModifier.Operation operation) {
        Identifier id = SaintsDragonsCommon.rl(path);
        return new AttributeModifier(id, amount, operation);
    }

    private ConfiguredItemAttributes() {
    }
}
