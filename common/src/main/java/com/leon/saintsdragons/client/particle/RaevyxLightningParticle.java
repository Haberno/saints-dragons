package com.leon.saintsdragons.client.particle;

import com.leon.saintsdragons.common.particle.raevyx.RaevyxLightningStormData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RaevyxLightningParticle extends SingleQuadParticle {
    private final SpriteSet spriteSet;
    private static final Vector3f[] CORNER_CACHE = new Vector3f[4];
    static {
        for (int i = 0; i < 4; i++) {
            CORNER_CACHE[i] = new Vector3f();
        }
    }

    protected RaevyxLightningParticle(ClientLevel level, double x, double y, double z,
                                      double xSpeed, double ySpeed, double zSpeed,
                                      float size, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.first());
        this.spriteSet = spriteSet;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize = size;
        this.lifetime = 8;
        this.setSize(size * 1.5F, size * 1.5F);
        updateSprite();
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            updateSprite();
        }
    }

    private void updateSprite() {
        float agePercent = (float) this.age / (float) this.lifetime;
        int spriteIndex = Math.min((int) (agePercent * 8), 7);
        this.setSprite(this.spriteSet.get(spriteIndex, 7));
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 240;
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<RaevyxLightningStormData> {
        private final SpriteSet spriteSet;
        public Factory(SpriteSet spriteSet) { this.spriteSet = spriteSet; }
        @Override
        public Particle createParticle(@Nonnull RaevyxLightningStormData data, @Nonnull ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new RaevyxLightningParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, data.size(), spriteSet);
        }
    }
}
