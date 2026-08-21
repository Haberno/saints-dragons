package com.leon.saintsdragons.common.particle;

import com.leon.saintsdragons.common.registry.ModParticles;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record SonicRingData(float yaw, float pitch, float scale, int duration) implements ParticleOptions {
    public static final MapCodec<SonicRingData> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            com.mojang.serialization.Codec.FLOAT.fieldOf("yaw").forGetter(SonicRingData::yaw),
            com.mojang.serialization.Codec.FLOAT.fieldOf("pitch").forGetter(SonicRingData::pitch),
            com.mojang.serialization.Codec.FLOAT.fieldOf("scale").forGetter(SonicRingData::scale),
            com.mojang.serialization.Codec.INT.fieldOf("duration").forGetter(SonicRingData::duration)
    ).apply(builder, SonicRingData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SonicRingData> STREAM_CODEC = StreamCodec.of(
            (buffer, value) -> {
                buffer.writeFloat(value.yaw);
                buffer.writeFloat(value.pitch);
                buffer.writeFloat(value.scale);
                buffer.writeInt(value.duration);
            },
            buffer -> new SonicRingData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt())
    );

    @Override
    public @NotNull ParticleType<SonicRingData> getType() {
        return ModParticles.RAEVYX_SONIC_RING.get();
    }
}
