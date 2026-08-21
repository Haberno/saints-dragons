package com.leon.saintsdragons.common.item;

import com.leon.saintsdragons.server.entity.npc.IvyTheDragonMerchant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public final class IvyOctopusPlushieItem extends Item {
    private static final int USE_COOLDOWN_TICKS = 40;
    private static final String BOUND_IVY_UUID_TAG = "BoundIvyUUID";
    private static final String BOUND_OWNER_UUID_TAG = "BoundOwnerUUID";

    public IvyOctopusPlushieItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player,
                                                           @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.FAIL;
        }
        if (player.getCooldowns().isOnCooldown(stack)) {
            return InteractionResult.FAIL;
        }

        UUID boundOwnerUuid = getBoundOwnerUuid(stack);
        if (boundOwnerUuid != null && !boundOwnerUuid.equals(player.getUUID())) {
            player.displayClientMessage(Component.translatable("message.saintsdragons.ivy_plushie.not_owner"), true);
            return InteractionResult.FAIL;
        }

        IvyTheDragonMerchant ivy = findLoadedIvy(serverPlayer, getBoundIvyUuid(stack));
        if (ivy == null) {
            player.displayClientMessage(Component.translatable("message.saintsdragons.ivy_plushie.not_loaded"), true);
            return InteractionResult.FAIL;
        }
        if (!ivy.summonNear(serverPlayer)) {
            player.displayClientMessage(Component.translatable("message.saintsdragons.ivy_plushie.no_safe_position"), true);
            return InteractionResult.FAIL;
        }

        bindTo(stack, ivy, serverPlayer);
        player.getCooldowns().addCooldown(stack, USE_COOLDOWN_TICKS);
        return InteractionResult.SUCCESS;
    }

    public static void bindTo(ItemStack stack, IvyTheDragonMerchant ivy, ServerPlayer owner) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.store(BOUND_IVY_UUID_TAG, UUIDUtil.CODEC, ivy.getUUID());
        tag.store(BOUND_OWNER_UUID_TAG, UUIDUtil.CODEC, owner.getUUID());
        CustomData.set(DataComponents.CUSTOM_DATA, stack, tag);
    }

    public static boolean canRepresent(ItemStack stack, IvyTheDragonMerchant ivy, ServerPlayer owner) {
        if (!(stack.getItem() instanceof IvyOctopusPlushieItem)) {
            return false;
        }
        UUID boundOwnerUuid = getBoundOwnerUuid(stack);
        UUID boundIvyUuid = getBoundIvyUuid(stack);
        if (boundOwnerUuid == null && boundIvyUuid == null) {
            return true;
        }
        return owner.getUUID().equals(boundOwnerUuid) && ivy.getUUID().equals(boundIvyUuid);
    }

    @Nullable
    private static UUID getBoundIvyUuid(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.read(BOUND_IVY_UUID_TAG, UUIDUtil.CODEC).orElse(null);
    }

    @Nullable
    private static UUID getBoundOwnerUuid(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.read(BOUND_OWNER_UUID_TAG, UUIDUtil.CODEC).orElse(null);
    }

    private static IvyTheDragonMerchant findLoadedIvy(ServerPlayer player, @Nullable UUID boundIvyUuid) {
        MinecraftServer server = player.level().getServer();
        if (server == null) {
            return null;
        }
        IvyTheDragonMerchant sameDimension = findLoadedIvy(player.level(), player, boundIvyUuid);
        if (sameDimension != null) {
            return sameDimension;
        }
        for (ServerLevel level : server.getAllLevels()) {
            if (level == player.level()) {
                continue;
            }
            IvyTheDragonMerchant ivy = findLoadedIvy(level, player, boundIvyUuid);
            if (ivy != null) {
                return ivy;
            }
        }
        return null;
    }

    private static IvyTheDragonMerchant findLoadedIvy(ServerLevel level, ServerPlayer player,
                                                       @Nullable UUID boundIvyUuid) {
        if (boundIvyUuid != null) {
            Entity entity = level.getEntity(boundIvyUuid);
            return entity instanceof IvyTheDragonMerchant ivy
                    && ivy.isAlive()
                    && ivy.isTame()
                    && ivy.isOwnedBy(player)
                    ? ivy
                    : null;
        }
        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof IvyTheDragonMerchant ivy
                    && ivy.isAlive()
                    && ivy.isTame()
                    && ivy.isOwnedBy(player)) {
                return ivy;
            }
        }
        return null;
    }
}
