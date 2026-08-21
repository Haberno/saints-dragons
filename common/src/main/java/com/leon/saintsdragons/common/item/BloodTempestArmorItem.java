package com.leon.saintsdragons.common.item;

import com.leon.saintsdragons.common.config.ToolsArmorConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import com.leon.saintsdragons.client.renderer.armor.BloodTempestArmorRenderer;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

public class BloodTempestArmorItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ArmorType type;

    public BloodTempestArmorItem(ArmorMaterial armorMaterial, ArmorType type, Properties properties) {
        super(configureProperties(properties, armorMaterial, type));
        this.type = type;
    }

    private static Properties configureProperties(Properties properties, ArmorMaterial material, ArmorType type) {
        return properties.humanoidArmor(material, type).attributes(ConfiguredItemAttributes.armor(
                type,
                configuredDefense(type),
                ToolsArmorConfig.BLOOD_TEMPEST_TOUGHNESS.get(),
                ToolsArmorConfig.BLOOD_TEMPEST_KNOCKBACK_RESISTANCE.get()));
    }

    public int getDefense() {
        return (int) Math.round(configuredDefense());
    }

    public float getToughness() {
        return (float) ToolsArmorConfig.BLOOD_TEMPEST_TOUGHNESS.get();
    }

    private double configuredDefense() {
        return configuredDefense(type);
    }

    private static double configuredDefense(ArmorType type) {
        return switch (type) {
            case HELMET -> ToolsArmorConfig.BLOOD_TEMPEST_HELMET_ARMOR.get();
            case CHESTPLATE -> ToolsArmorConfig.BLOOD_TEMPEST_CHESTPLATE_ARMOR.get();
            case LEGGINGS -> ToolsArmorConfig.BLOOD_TEMPEST_LEGGINGS_ARMOR.get();
            case BOOTS -> ToolsArmorConfig.BLOOD_TEMPEST_BOOTS_ARMOR.get();
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
        tooltip.accept(Component.empty()
                .append(Component.translatable("item.saintsdragons.blood_tempest_armor.tooltip.title")
                        .withStyle(ChatFormatting.DARK_RED))
                .append(Component.literal(" "))
                .append(Component.translatable("item.saintsdragons.blood_tempest_armor.tooltip.full_set")
                        .withStyle(ChatFormatting.GRAY)));
        tooltip.accept(Component.translatable("item.saintsdragons.blood_tempest_armor.tooltip.description")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private BloodTempestArmorRenderer renderer;

            @Override
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack stack, EquipmentSlot slot) {
                if (this.renderer == null) {
                    this.renderer = new BloodTempestArmorRenderer();
                }
                return this.renderer;
            }
        });
    }

    public void initializeClient(Consumer<Object> consumer) {
        try {
            Class<?> extensions = Class.forName("net.minecraftforge.client.extensions.common.IClientItemExtensions");
            Object proxy = Proxy.newProxyInstance(
                    BloodTempestArmorItem.class.getClassLoader(),
                    new Class<?>[]{extensions},
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
            Class<?> provider = Class.forName("com.leon.saintsdragons.client.renderer.armor.BloodTempestRenderProvider");
            return provider.getMethod("getHumanoidArmorModel", Object.class, Object.class, Object.class, Object.class)
                    .invoke(null, args[0], args[1], args[2], args[3]);
        } catch (ReflectiveOperationException ignored) {
            return args[3];
        }
    }

    private static Object defaultForgeExtensionValue(Class<?> returnType) {
        if (returnType == Boolean.TYPE) {
            return false;
        }
        if (returnType == Byte.TYPE) {
            return (byte) 0;
        }
        if (returnType == Short.TYPE) {
            return (short) 0;
        }
        if (returnType == Integer.TYPE) {
            return 0;
        }
        if (returnType == Long.TYPE) {
            return 0L;
        }
        if (returnType == Float.TYPE) {
            return 0.0F;
        }
        if (returnType == Double.TYPE) {
            return 0.0D;
        }
        if (returnType == Character.TYPE) {
            return '\0';
        }
        return null;
    }
}
