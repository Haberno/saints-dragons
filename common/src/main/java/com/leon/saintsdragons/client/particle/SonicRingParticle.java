package com.leon.saintsdragons.client.particle;

import com.leon.saintsdragons.common.particle.SonicRingData;
import com.mojang.math.Axis;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class SonicRingParticle extends SingleQuadParticle {
    private final SpriteSet sprites;
    private final float yaw;
    private final float pitch;
    private final float baseSize;

    protected SonicRingParticle(ClientLevel level, double x, double y, double z,
                                double xSpeed, double ySpeed, double zSpeed,
                                SonicRingData data, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites.first());
        this.sprites = sprites;
        this.yaw = data.yaw();
        this.pitch = data.pitch();
        this.baseSize = data.scale();
        this.lifetime = Math.max(1, data.duration());
        this.alpha = 0.95F;
        this.hasPhysics = false;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.setSpriteFromAge(this.sprites);
        this.alpha = Math.max(0.0F, 0.95F * (1.0F - this.age / (float) this.lifetime));
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.86D;
        this.yd *= 0.86D;
        this.zd *= 0.86D;
    }

    @Override
    public float getQuadSize(float partialTick) {
        float progress = (this.age + partialTick) / this.lifetime;
        return this.baseSize * (1.0F - progress - (float) Math.pow(2000.0D, -progress));
    }

    @Override
    public FacingCameraMode getFacingCameraMode() {
        return (rotation, camera, partialTick) -> rotation.identity()
                .mul(Axis.YP.rotation(this.yaw))
                .mul(Axis.XP.rotation(this.pitch));
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240 | super.getLightColor(partialTick) & 0xFF0000;
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SonicRingData> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(@NotNull SonicRingData type, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new SonicRingParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, type, this.sprites);
        }
    }
}
