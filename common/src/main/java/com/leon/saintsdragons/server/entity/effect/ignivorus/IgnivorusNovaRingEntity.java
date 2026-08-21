package com.leon.saintsdragons.server.entity.effect.ignivorus;

import com.leon.saintsdragons.common.registry.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class IgnivorusNovaRingEntity extends Entity {

    private static final int DURATION = 20;

    private int age;

    public IgnivorusNovaRingEntity(EntityType<? extends IgnivorusNovaRingEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public IgnivorusNovaRingEntity(Level level, Vec3 position) {
        this(ModEntities.IGNIVORUS_NOVA_RING.get(), level);
        setPos(position);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    public int getAge() {
        return this.age;
    }

    public int getDuration() {
        return DURATION;
    }

    public float getScale(float partialTicks) {
        float ageFrac = (this.age + partialTicks) / (float) DURATION;
        return ageFrac * 5.0F;
    }

    public float getOpacity(float partialTicks) {
        float ageFrac = (this.age + partialTicks) / (float) DURATION;
        return (float) Math.max((1.0 - ageFrac * ageFrac) * 0.8, 0.0);
    }

    @Override
    public void tick() {
        super.tick();
        this.age++;

        if (!this.level().isClientSide()) {
            if (this.age >= DURATION) {
                this.discard();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput tag) {
        this.age = tag.getIntOr("Age", 0);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput tag) {
        tag.putInt("Age", this.age);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 16384.0;
    }

    @Override
    public boolean hurtServer(@NotNull ServerLevel level, @NotNull DamageSource source, float amount) {
        return false;
    }
}
