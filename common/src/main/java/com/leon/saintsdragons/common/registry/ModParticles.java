package com.leon.saintsdragons.common.registry;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.particle.raevyx.RaevyxLightningChainData;
import com.leon.saintsdragons.common.particle.raevyx.RaevyxLightningStormData;
import com.leon.saintsdragons.common.particle.SonicRingData;
import com.leon.saintsdragons.platform.RegistryHelper;
import com.leon.saintsdragons.platform.Services;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public final class ModParticles {
    private static final RegistryHelper.RegistryWrapper<ParticleType<?>> REGISTER =
            Services.PLATFORM.getRegistryHelper()
                    .create(Registries.PARTICLE_TYPE, () -> BuiltInRegistries.PARTICLE_TYPE, SaintsDragonsCommon.MOD_ID);

    public static final Supplier<ParticleType<RaevyxLightningStormData>> LIGHTNING_STORM =
            REGISTER.register("lightning_storm",
                    () -> new ParticleType<>(false) {
                        @Override
                        public com.mojang.serialization.MapCodec<RaevyxLightningStormData> codec() {
                            return RaevyxLightningStormData.codec(false);
                        }

                        @Override
                        public net.minecraft.network.codec.StreamCodec<? super net.minecraft.network.RegistryFriendlyByteBuf, RaevyxLightningStormData> streamCodec() {
                            return RaevyxLightningStormData.streamCodec(false);
                        }
                    });

    public static final Supplier<ParticleType<RaevyxLightningStormData>> LIGHTNING_STORM_NIGHT_GOLD =
            REGISTER.register("lightning_storm_night_gold",
                    () -> new ParticleType<>(false) {
                        @Override
                        public com.mojang.serialization.MapCodec<RaevyxLightningStormData> codec() {
                            return RaevyxLightningStormData.codec(true);
                        }

                        @Override
                        public net.minecraft.network.codec.StreamCodec<? super net.minecraft.network.RegistryFriendlyByteBuf, RaevyxLightningStormData> streamCodec() {
                            return RaevyxLightningStormData.streamCodec(true);
                        }
                    });

    public static final Supplier<ParticleType<RaevyxLightningChainData>> LIGHTNING_CHAIN =
            REGISTER.register("lightning_chain",
                    () -> new ParticleType<>(false) {
                        @Override
                        public com.mojang.serialization.MapCodec<RaevyxLightningChainData> codec() {
                            return RaevyxLightningChainData.CODEC;
                        }

                        @Override
                        public net.minecraft.network.codec.StreamCodec<? super net.minecraft.network.RegistryFriendlyByteBuf, RaevyxLightningChainData> streamCodec() {
                            return RaevyxLightningChainData.STREAM_CODEC;
                        }
                    });

    public static final Supplier<ParticleType<SonicRingData>> RAEVYX_SONIC_RING =
            REGISTER.register("raevyx_sonic_ring",
                    () -> new ParticleType<>(false) {
                        @Override
                        public com.mojang.serialization.MapCodec<SonicRingData> codec() {
                            return SonicRingData.CODEC;
                        }

                        @Override
                        public net.minecraft.network.codec.StreamCodec<? super net.minecraft.network.RegistryFriendlyByteBuf, SonicRingData> streamCodec() {
                            return SonicRingData.STREAM_CODEC;
                        }
                    });

    public static final Supplier<SimpleParticleType> FIRE_BREATH_FLAME =
            REGISTER.register("fire_breath_flame", () -> Services.PLATFORM.createSimpleParticle(false));

    public static final Supplier<SimpleParticleType> FIRE_BREATH_SMOKE =
            REGISTER.register("fire_breath_smoke", () -> Services.PLATFORM.createSimpleParticle(false));

    public static final Supplier<SimpleParticleType> DRAGON_DUST =
            REGISTER.register("dragon_dust", () -> Services.PLATFORM.createSimpleParticle(true));

    public static final Supplier<SimpleParticleType> MOSSBACK_POISON_FUME =
            REGISTER.register("mossback_poison_fume", () -> Services.PLATFORM.createSimpleParticle(false));

    public static final Supplier<SimpleParticleType> DRACONIAN_NUCLEUS_PARTICLE =
            REGISTER.register("draconian_nucleus_particle", () -> Services.PLATFORM.createSimpleParticle(true));

    public static final Supplier<SimpleParticleType> ATROXIIA_SNOW =
            REGISTER.register("atroxiia_snow", () -> Services.PLATFORM.createSimpleParticle(false));

    public static final Supplier<SimpleParticleType> ATROXIIA_SNOW_SHARD =
            REGISTER.register("atroxiia_snow_shard", () -> Services.PLATFORM.createSimpleParticle(false));

    public static final Supplier<SimpleParticleType> ATROXIIA_SNOW_SPARK =
            REGISTER.register("atroxiia_snow_spark", () -> Services.PLATFORM.createSimpleParticle(false));

    public static final Supplier<SimpleParticleType> ATROXIIA_SNOW_DUST =
            REGISTER.register("atroxiia_snow_dust", () -> Services.PLATFORM.createSimpleParticle(false));

    private ModParticles() {
    }

    public static void register() {
        REGISTER.register();
    }
}
