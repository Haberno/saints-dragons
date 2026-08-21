package com.leon.saintsdragons.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FixedPotionItem extends PotionItem {
    private final Supplier<Potion> potion;

    public FixedPotionItem(Item.Properties properties, Supplier<Potion> potion) {
        super(properties);
        this.potion = potion;
    }

    private ItemStack ensurePotion(ItemStack stack) {
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        if (contents.potion().isEmpty()) {
            Holder<Potion> holder = BuiltInRegistries.POTION.wrapAsHolder(this.potion.get());
            stack.set(DataComponents.POTION_CONTENTS, contents.withPotion(holder));
        }
        return stack;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return ensurePotion(super.getDefaultInstance());
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ensurePotion(stack);
        return super.use(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (!level.isClientSide()) {
            for (MobEffectInstance effectInstance : this.potion.get().getEffects()) {
                if (effectInstance.getEffect().value().isInstantenous()) {
                    effectInstance.getEffect().value().applyInstantenousEffect(
                            (ServerLevel) level,
                            null,
                            null,
                            livingEntity,
                            effectInstance.getAmplifier(),
                            1.0D
                    );
                } else {
                    livingEntity.addEffect(new MobEffectInstance(effectInstance));
                }
            }
        }

        if (livingEntity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        } else {
            stack.shrink(1);
        }

        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        PotionContents.addPotionTooltip(this.potion.get().getEffects(), tooltipComponents, 1.0F, context.tickRate());
    }
}
