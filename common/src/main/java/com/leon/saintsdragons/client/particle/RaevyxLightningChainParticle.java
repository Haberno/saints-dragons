package com.leon.saintsdragons.client.particle;

import com.leon.saintsdragons.common.particle.raevyx.RaevyxLightningChainData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class RaevyxLightningChainParticle extends SingleQuadParticle {
    private final SpriteSet spriteSet;
    private final Vec3 startPos;
    private final Vec3 endPos;
    private final long renderSeed;

    protected RaevyxLightningChainParticle(ClientLevel level, double x, double y, double z,
                                           double xSpeed, double ySpeed, double zSpeed,
                                           float size, SpriteSet spriteSet, Vec3 startPos, Vec3 endPos) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.first());
        this.spriteSet = spriteSet;
        this.startPos = startPos;
        this.endPos = endPos;
        this.renderSeed = BlockPos.containing(startPos).asLong() ^ BlockPos.containing(endPos).asLong();
        this.quadSize = size;
        this.lifetime = 6;
        this.setSize(size * 2.4F, size * 2.4F);
        this.setSpriteFromAge(spriteSet);

        Vec3 midpoint = startPos.lerp(endPos, 0.5D);
        this.setPos(midpoint.x, midpoint.y, midpoint.z);
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
            float fadeProgress = (this.age + 1.0F) / (float) this.lifetime;
            this.alpha = fadeProgress > 0.65F ? 1.0F - ((fadeProgress - 0.65F) / 0.35F) : 1.0F;
        }
    }

    private void updateSprite() {
        this.setSpriteFromAge(this.spriteSet);
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
    public static class Factory implements ParticleProvider<RaevyxLightningChainData> {
        private final SpriteSet spriteSet;
        public Factory(SpriteSet spriteSet) { this.spriteSet = spriteSet; }
        
        @Override
        public Particle createParticle(@Nonnull RaevyxLightningChainData data, @Nonnull ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            try {
                return new RaevyxLightningChainParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, data.size(), spriteSet, data.startPos(), data.endPos());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
    }
}
