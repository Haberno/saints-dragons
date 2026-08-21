package com.leon.saintsdragons.fabric.platform;

import com.leon.saintsdragons.platform.NetworkHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FabricNetworkHelper implements NetworkHelper {
    private enum Direction {
        SERVERBOUND,
        CLIENTBOUND
    }

    private static final class Binding<T> {
        final Identifier id;
        final CustomPacketPayload.Type<RawPayload> payloadType;
        final PacketEncoder<T> encoder;
        final Direction direction;

        Binding(Identifier id, PacketEncoder<T> encoder, Direction direction) {
            this.id = id;
            this.payloadType = new CustomPacketPayload.Type<>(id);
            this.encoder = encoder;
            this.direction = direction;
        }
    }

    private record RawPayload(CustomPacketPayload.Type<RawPayload> type, byte[] data)
            implements CustomPacketPayload {
    }

    private final Map<Class<?>, Binding<?>> bindings = new ConcurrentHashMap<>();

    @Override
    public <T> void registerServerbound(Class<T> type,
                                        Identifier id,
                                        PacketEncoder<T> encoder,
                                        PacketDecoder<T> decoder,
                                        ServerboundHandler<T> handler) {
        Binding<T> binding = new Binding<>(id, encoder, Direction.SERVERBOUND);
        bindings.put(type, binding);
        PayloadTypeRegistry.playC2S().register(binding.payloadType, codec(binding.payloadType));
        ServerPlayNetworking.registerGlobalReceiver(binding.payloadType, (payload, context) -> {
            T message = decoder.decode(readBuffer(payload));
            context.server().execute(() -> handler.handle(message, context.player()));
        });
    }

    @Override
    public <T> void registerClientbound(Class<T> type,
                                        Identifier id,
                                        PacketEncoder<T> encoder,
                                        PacketDecoder<T> decoder,
                                        ClientboundHandler<T> handler) {
        Binding<T> binding = new Binding<>(id, encoder, Direction.CLIENTBOUND);
        bindings.put(type, binding);
        PayloadTypeRegistry.playS2C().register(binding.payloadType, codec(binding.payloadType));
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientAccess.register(binding.payloadType, decoder, handler);
        }
    }

    @Override
    public void sendToServer(Object message) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            throw new IllegalStateException("Client-only networking method invoked on a non-client environment");
        }
        Binding<Object> binding = bindingFor(message);
        if (binding.direction != Direction.SERVERBOUND) {
            throw new IllegalStateException("Attempted to send clientbound packet to server: " + message.getClass());
        }
        ClientAccess.send(createPayload(binding, message));
    }

    @Override
    public void sendToPlayer(ServerPlayer player, Object message) {
        Binding<Object> binding = bindingFor(message);
        if (binding.direction != Direction.CLIENTBOUND) {
            throw new IllegalStateException("Attempted to send serverbound packet to player: " + message.getClass());
        }
        ServerPlayNetworking.send(player, createPayload(binding, message));
    }

    @Override
    public void sendToTracking(Entity entity, Object message) {
        Binding<Object> binding = bindingFor(message);
        if (binding.direction != Direction.CLIENTBOUND) {
            throw new IllegalStateException("Attempted to send serverbound packet to tracking players: " + message.getClass());
        }
        for (ServerPlayer tracking : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(tracking, createPayload(binding, message));
        }
    }

    @Override
    public void sendToDimension(Level level, Object message) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        Binding<Object> binding = bindingFor(message);
        if (binding.direction != Direction.CLIENTBOUND) {
            throw new IllegalStateException("Attempted to send serverbound packet to dimension: " + message.getClass());
        }
        for (ServerPlayer player : PlayerLookup.world(serverLevel)) {
            ServerPlayNetworking.send(player, createPayload(binding, message));
        }
    }

    private Binding<Object> bindingFor(Object message) {
        Class<?> messageClass = message.getClass();
        @SuppressWarnings("unchecked")
        Binding<Object> binding = (Binding<Object>) bindings.get(messageClass);
        if (binding == null) {
            throw new IllegalStateException("No network binding registered for " + messageClass.getName());
        }
        return binding;
    }

    private RawPayload createPayload(Binding<Object> binding, Object message) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        @SuppressWarnings("unchecked")
        PacketEncoder<Object> encoder = (PacketEncoder<Object>) binding.encoder;
        encoder.encode(message, buffer);
        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        buffer.release();
        return new RawPayload(binding.payloadType, data);
    }

    private static FriendlyByteBuf readBuffer(RawPayload payload) {
        return new FriendlyByteBuf(io.netty.buffer.Unpooled.wrappedBuffer(payload.data()));
    }

    private static StreamCodec<RegistryFriendlyByteBuf, RawPayload> codec(
            CustomPacketPayload.Type<RawPayload> payloadType) {
        return StreamCodec.composite(
                ByteBufCodecs.BYTE_ARRAY,
                RawPayload::data,
                data -> new RawPayload(payloadType, data)
        );
    }

    @Environment(EnvType.CLIENT)
    private static final class ClientAccess {
        private ClientAccess() {}

        private static <T> void register(CustomPacketPayload.Type<RawPayload> payloadType,
                                         PacketDecoder<T> decoder,
                                         ClientboundHandler<T> handler) {
            ClientPlayNetworking.registerGlobalReceiver(payloadType, (payload, context) -> {
                T message = decoder.decode(readBuffer(payload));
                context.client().execute(() -> handler.handle(message));
            });
        }

        private static void send(RawPayload payload) {
            ClientPlayNetworking.send(payload);
        }
    }
}
