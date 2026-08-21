package com.leon.saintsdragons.common.item;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import com.leon.saintsdragons.common.config.ToolsArmorConfig;
import com.leon.saintsdragons.common.registry.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import com.leon.saintsdragons.client.renderer.armor.DragonlordArmorRenderer;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;


import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

public class DragonlordArmorItem extends Item implements GeoItem {
    public static final String FLIGHT_CONTROLLER = "dragonlord_flight";
    public static final String FLAP_TRIGGER = "flap";
    private static final RawAnimation GLIDE =
            RawAnimation.begin().thenLoop("animation.dragonlord_armor.glide");
    private static final RawAnimation FLAP =
            RawAnimation.begin().thenPlay("animation.dragonlord_armor.flap");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ArmorType type;

    public DragonlordArmorItem(ArmorMaterial material, ArmorType type, Properties properties) {
        super(configureProperties(properties, material, type));
        this.type = type;
        GeoItem.registerSyncedAnimatable(this);
    }

    private static Properties configureProperties(Properties properties, ArmorMaterial material, ArmorType type) {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
        ItemAttributeModifiers attributes = ConfiguredItemAttributes.armor(
                        type,
                        configuredDefense(type),
                        ToolsArmorConfig.DRAGONLORD_TOUGHNESS.get(),
                        configuredKnockbackResistance(type))
                .withModifierAdded(Attributes.MAX_HEALTH,
                        new AttributeModifier(SaintsDragonsCommon.rl("dragonlord_max_health_" + type.getName()),
                                maxHealthMultiplier(type), AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        slot)
                .withModifierAdded(Holder.direct(ModAttributes.FIRE_RESISTANCE.get()),
                        new AttributeModifier(SaintsDragonsCommon.rl("dragonlord_fire_resistance_" + type.getName()),
                                ToolsArmorConfig.DRAGONLORD_FIRE_RESISTANCE.get(),
                                AttributeModifier.Operation.ADD_VALUE),
                        slot)
                .withModifierAdded(Holder.direct(ModAttributes.BLAST_RESISTANCE.get()),
                        new AttributeModifier(SaintsDragonsCommon.rl("dragonlord_blast_resistance_" + type.getName()),
                                ToolsArmorConfig.DRAGONLORD_BLAST_RESISTANCE.get(),
                                AttributeModifier.Operation.ADD_VALUE),
                        slot);
        return properties.humanoidArmor(material, type).attributes(attributes);
    }

    private double configuredKnockbackResistance() {
        return configuredKnockbackResistance(type);
    }

    private static double configuredKnockbackResistance(ArmorType type) {
        return switch (type) {
            case HELMET -> ToolsArmorConfig.DRAGONLORD_HELMET_KNOCKBACK_RESISTANCE.get();
            case CHESTPLATE -> ToolsArmorConfig.DRAGONLORD_CHESTPLATE_KNOCKBACK_RESISTANCE.get();
            case LEGGINGS -> ToolsArmorConfig.DRAGONLORD_LEGGINGS_KNOCKBACK_RESISTANCE.get();
            case BOOTS -> ToolsArmorConfig.DRAGONLORD_BOOTS_KNOCKBACK_RESISTANCE.get();
            case BODY -> 0.0D;
        };
    }

    private double maxHealthMultiplier() {
        return maxHealthMultiplier(type);
    }

    private static double maxHealthMultiplier(ArmorType type) {
        return switch (type) {
            case HELMET -> ToolsArmorConfig.DRAGONLORD_HELMET_MAX_HEALTH_BONUS.get() / 100.0D;
            case CHESTPLATE -> ToolsArmorConfig.DRAGONLORD_CHESTPLATE_MAX_HEALTH_BONUS.get() / 100.0D;
            case LEGGINGS -> ToolsArmorConfig.DRAGONLORD_LEGGINGS_MAX_HEALTH_BONUS.get() / 100.0D;
            case BOOTS -> ToolsArmorConfig.DRAGONLORD_BOOTS_MAX_HEALTH_BONUS.get() / 100.0D;
            case BODY -> 0.0D;
        };
    }

    public int getDefense() {
        return (int) Math.round(configuredDefense());
    }

    public float getToughness() {
        return (float) ToolsArmorConfig.DRAGONLORD_TOUGHNESS.get();
    }

    private double configuredDefense() {
        return configuredDefense(type);
    }

    private static double configuredDefense(ArmorType type) {
        return switch (type) {
            case HELMET -> ToolsArmorConfig.DRAGONLORD_HELMET_ARMOR.get();
            case CHESTPLATE -> ToolsArmorConfig.DRAGONLORD_CHESTPLATE_ARMOR.get();
            case LEGGINGS -> ToolsArmorConfig.DRAGONLORD_LEGGINGS_ARMOR.get();
            case BOOTS -> ToolsArmorConfig.DRAGONLORD_BOOTS_ARMOR.get();
            case BODY -> 0.0D;
        };
    }

    public ArmorType getType() {
        return type;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.double_jump.title")
                .withStyle(ChatFormatting.GOLD));
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.double_jump.description")
                .withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.dragonfall.title")
                .withStyle(ChatFormatting.GOLD));
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.dragonfall.description")
                .withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.aerial_dominion.title")
                .withStyle(ChatFormatting.GOLD));
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.aerial_dominion.enter")
                .withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable("item.saintsdragons.dragonlord_armor.tooltip.aerial_dominion.boost")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<DragonlordArmorItem> flightController = new AnimationController<>(
                FLIGHT_CONTROLLER,
                3,
                state -> {
                    Entity wearer = state.getData(GeoRenderDataTickets.ARMOR_WEARER);
                    if (!(wearer instanceof LivingEntity living)
                            || !living.isFallFlying()
                            || !DragonlordArmorSetBonus.isWearingFullSet(living)) {
                        return PlayState.STOP;
                    }

                    state.setAndContinue(GLIDE);
                    return PlayState.CONTINUE;
                }
        );
        flightController.triggerableAnim(FLAP_TRIGGER, FLAP);
        controllers.add(flightController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private DragonlordArmorRenderer renderer;

            @Override
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack stack, EquipmentSlot slot) {
                if (this.renderer == null) {
                    this.renderer = new DragonlordArmorRenderer();
                }
                return this.renderer;
            }
        });
    }

    public void initializeClient(Consumer<Object> consumer) {
        try {
            Class<?> extensions = Class.forName("net.minecraftforge.client.extensions.common.IClientItemExtensions");
            Object proxy = Proxy.newProxyInstance(DragonlordArmorItem.class.getClassLoader(), new Class<?>[]{extensions},
                    (proxyInstance, method, args) -> {
                        if ("getHumanoidArmorModel".equals(method.getName()) || "getGenericArmorModel".equals(method.getName())) {
                            return getHumanoidArmorModel(args);
                        }
                        return defaultForgeExtensionValue(method.getReturnType());
                    });
            consumer.accept(proxy);
        } catch (ClassNotFoundException ignored) {
        }
    }

    private Object getHumanoidArmorModel(Object[] args) {
        if (args == null || args.length < 4) {
            return null;
        }
        try {
            Class<?> provider = Class.forName("com.leon.saintsdragons.client.renderer.armor.DragonlordArmorRenderProvider");
            return provider.getMethod("getHumanoidArmorModel", Object.class, Object.class, Object.class, Object.class)
                    .invoke(null, args[0], args[1], args[2], args[3]);
        } catch (ReflectiveOperationException ignored) {
            return args[3];
        }
    }

    private static Object defaultForgeExtensionValue(Class<?> type) {
        if (type == Boolean.TYPE) return false;
        if (type == Byte.TYPE) return (byte) 0;
        if (type == Short.TYPE) return (short) 0;
        if (type == Integer.TYPE) return 0;
        if (type == Long.TYPE) return 0L;
        if (type == Float.TYPE) return 0.0F;
        if (type == Double.TYPE) return 0.0D;
        if (type == Character.TYPE) return '\0';
        return null;
    }
}
