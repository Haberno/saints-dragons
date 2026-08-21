package com.leon.saintsdragons.common.particle.raevyx;

import com.leon.saintsdragons.common.registry.ModParticles;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record RaevyxLightningChainData(float size, Vec3 startPos, Vec3 endPos) implements ParticleOptions {
    public static final MapCodec<RaevyxLightningChainData> CODEC =
            RecordCodecBuilder.mapCodec(builder -> builder.group(
                    com.mojang.serialization.Codec.FLOAT.fieldOf("size").forGetter(RaevyxLightningChainData::size),
                    Vec3.CODEC.fieldOf("startPos").forGetter(RaevyxLightningChainData::startPos),
                    Vec3.CODEC.fieldOf("endPos").forGetter(RaevyxLightningChainData::endPos)
            ).apply(builder, RaevyxLightningChainData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RaevyxLightningChainData> STREAM_CODEC =
            StreamCodec.of(
                    (buffer, value) -> {
                        buffer.writeFloat(value.size);
                        buffer.writeVec3(value.startPos);
                        buffer.writeVec3(value.endPos);
                    },
                    buffer -> new RaevyxLightningChainData(
                            buffer.readFloat(), buffer.readVec3(), buffer.readVec3())
            );

    @Override
    public @NotNull ParticleType<RaevyxLightningChainData> getType() {
        return ModParticles.LIGHTNING_CHAIN.get();
    }
}
