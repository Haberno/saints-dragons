package com.leon.saintsdragons.fabric.resource;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.config.dragon.DragonAttributeConfigLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Fabric wrapper that adapts the common dragon attribute loader to the identifiable listener API.
 */
public final class FabricDragonAttributeReloadListener implements IdentifiableResourceReloadListener {
    private static final Identifier ID = SaintsDragonsCommon.rl("dragon_attribute_loader");
    private final DragonAttributeConfigLoader delegate = DragonAttributeConfigLoader.getInstance();

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public @NotNull CompletableFuture<Void> reload(PreparableReloadListener.@NotNull SharedState state,
                                                   @NotNull Executor prepareExecutor,
                                                   PreparableReloadListener.@NotNull PreparationBarrier barrier,
                                                   @NotNull Executor applyExecutor) {
        return delegate.reload(state, prepareExecutor, barrier, applyExecutor);
    }
}
