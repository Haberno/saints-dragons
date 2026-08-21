package com.leon.saintsdragons.common.particle.raevyx;

import com.leon.saintsdragons.common.registry.ModParticles;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record RaevyxLightningStormData(float size, boolean nightGold) implements ParticleOptions {
    public RaevyxLightningStormData(float size) {
        this(size, false);
    }

    public static MapCodec<RaevyxLightningStormData> codec(boolean nightGold) {
        return RecordCodecBuilder.mapCodec(builder -> builder.group(
                com.mojang.serialization.Codec.FLOAT.fieldOf("size").forGetter(RaevyxLightningStormData::size)
        ).apply(builder, size -> new RaevyxLightningStormData(size, nightGold)));
    }

    public static StreamCodec<RegistryFriendlyByteBuf, RaevyxLightningStormData> streamCodec(boolean nightGold) {
        return StreamCodec.of(
                (buffer, value) -> buffer.writeFloat(value.size),
                buffer -> new RaevyxLightningStormData(buffer.readFloat(), nightGold)
        );
    }

    @Override
    public @NotNull ParticleType<RaevyxLightningStormData> getType() {
        return nightGold ? ModParticles.LIGHTNING_STORM_NIGHT_GOLD.get() : ModParticles.LIGHTNING_STORM.get();
    }
}
