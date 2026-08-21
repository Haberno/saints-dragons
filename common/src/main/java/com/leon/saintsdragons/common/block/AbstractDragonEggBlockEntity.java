package com.leon.saintsdragons.common.block;

import com.leon.saintsdragons.server.entity.base.DragonGender;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;


public abstract class AbstractDragonEggBlockEntity extends BlockEntity {
    private static final double LEGACY_NORMAL_HATCH_TICKS = 18000.0D;

    private double hatchProgress;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    private UUID hatchAdvancementOwnerUUID;
    @Nullable
    private DragonGender babyGender;
    private boolean pausedHatchingParticlesShown;

    protected AbstractDragonEggBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public double getHatchProgress() {
        return hatchProgress;
    }

    public void setHatchProgress(double hatchProgress) {
        this.hatchProgress = Math.max(0.0D, Math.min(1.0D, hatchProgress));
        this.setChanged();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwnerUUID(@Nullable UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        this.setChanged();
    }

    @Nullable
    public UUID getHatchAdvancementOwnerUUID() {
        return this.hatchAdvancementOwnerUUID != null ? this.hatchAdvancementOwnerUUID : this.ownerUUID;
    }

    public void setHatchAdvancementOwnerUUID(@Nullable UUID hatchAdvancementOwnerUUID) {
        this.hatchAdvancementOwnerUUID = hatchAdvancementOwnerUUID;
        this.setChanged();
    }

    @Nullable
    public DragonGender getBabyGender() {
        return this.babyGender;
    }

    public void setBabyGender(@Nullable DragonGender gender) {
        this.babyGender = gender;
        this.setChanged();
    }

    public boolean hasShownPausedHatchingParticles() {
        return this.pausedHatchingParticlesShown;
    }

    public void setPausedHatchingParticlesShown(boolean shown) {
        if (this.pausedHatchingParticlesShown != shown) {
            this.pausedHatchingParticlesShown = shown;
            this.setChanged();
        }
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput tag) {
        super.saveAdditional(tag);
        tag.putDouble("HatchProgress", this.hatchProgress);
        if (this.ownerUUID != null) {
            tag.store("OwnerUUID", UUIDUtil.CODEC, this.ownerUUID);
        }
        if (this.hatchAdvancementOwnerUUID != null) {
            tag.store("HatchAdvancementOwnerUUID", UUIDUtil.CODEC, this.hatchAdvancementOwnerUUID);
        }
        if (this.babyGender != null) {
            tag.putByte("BabyGender", this.babyGender.getId());
        }
        tag.putBoolean("PausedHatchingParticlesShown", this.pausedHatchingParticlesShown);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput tag) {
        super.loadAdditional(tag);
        double savedProgress = tag.getDoubleOr("HatchProgress", Double.NaN);
        if (!Double.isNaN(savedProgress)) {
            this.hatchProgress = Math.max(0.0D, Math.min(1.0D, savedProgress));
        } else {
            double legacyTicks = Math.max(0, tag.getIntOr("HatchProgressTicks", 0));
            this.hatchProgress = Math.max(0.0D, Math.min(1.0D, legacyTicks / LEGACY_NORMAL_HATCH_TICKS));
        }
        this.ownerUUID = tag.read("OwnerUUID", UUIDUtil.CODEC).orElse(null);
        this.hatchAdvancementOwnerUUID = tag.read("HatchAdvancementOwnerUUID", UUIDUtil.CODEC).orElse(null);
        byte savedGender = tag.getByteOr("BabyGender", (byte) -1);
        this.babyGender = savedGender >= 0 ? DragonGender.fromId(savedGender) : null;
        this.pausedHatchingParticlesShown = tag.getBooleanOr("PausedHatchingParticlesShown", false);
    }
}
