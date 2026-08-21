package com.leon.saintsdragons.forge.platform;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.platform.NetworkHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import java.util.concurrent.atomic.AtomicInteger;

public final class ForgeNetworkHelper implements NetworkHelper {
    private static final int PROTOCOL_VERSION = 1;
    private SimpleChannel channel;
    private boolean built;
    private final AtomicInteger nextId = new AtomicInteger();

    public ForgeNetworkHelper() {
        // Lazy initialization - defer channel creation until first use
        // to avoid ServiceConfigurationError during early class loading
    }

    private SimpleChannel getChannel() {
        if (channel == null) {
            channel = ChannelBuilder
                    .named(Identifier.fromNamespaceAndPath(SaintsDragonsCommon.MOD_ID, "main"))
                    .networkProtocolVersion(PROTOCOL_VERSION)
                    .simpleChannel();
        }
        return channel;
    }

    @Override
    public <T> void registerServerbound(Class<T> type,
                                        Identifier id,
                                        PacketEncoder<T> encoder,
                                        PacketDecoder<T> decoder,
                                        ServerboundHandler<T> handler) {
        getChannel().messageBuilder(type, nextId.getAndIncrement())
                .encoder(encoder::encode)
                .decoder(decoder::decode)
                .consumerMainThread((message, context) -> {
                    ServerPlayer sender = context.getSender();
                    if (sender != null) {
                        handler.handle(message, sender);
                    }
                })
                .add();
    }

    @Override
    public <T> void registerClientbound(Class<T> type,
                                        Identifier id,
                                        PacketEncoder<T> encoder,
                                        PacketDecoder<T> decoder,
                                        ClientboundHandler<T> handler) {
        getChannel().messageBuilder(type, nextId.getAndIncrement())
                .encoder(encoder::encode)
                .decoder(decoder::decode)
                .consumerMainThread((message, context) -> handler.handle(message))
                .add();
    }

    @Override
    public void sendToServer(Object message) {
        ensureBuilt().send(message, PacketDistributor.SERVER.noArg());
    }

    @Override
    public void sendToPlayer(ServerPlayer player, Object message) {
        ensureBuilt().send(message, PacketDistributor.PLAYER.with(player));
    }

    @Override
    public void sendToTracking(Entity entity, Object message) {
        ensureBuilt().send(message, PacketDistributor.TRACKING_ENTITY.with(entity));
    }

    @Override
    public void sendToDimension(Level level, Object message) {
        ensureBuilt().send(message, PacketDistributor.DIMENSION.with(level.dimension()));
    }

    private SimpleChannel ensureBuilt() {
        SimpleChannel current = getChannel();
        if (!built) {
            current.build();
            built = true;
        }
        return current;
    }
}
