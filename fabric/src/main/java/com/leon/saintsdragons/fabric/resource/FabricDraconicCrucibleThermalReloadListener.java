package com.leon.saintsdragons.fabric.resource;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.block.crucible.DraconicCrucibleThermalReloadListener;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class FabricDraconicCrucibleThermalReloadListener implements IdentifiableResourceReloadListener {
    private static final Identifier ID = SaintsDragonsCommon.rl("draconic_crucible_thermal");
    private final DraconicCrucibleThermalReloadListener delegate =
            DraconicCrucibleThermalReloadListener.getInstance();

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public @NotNull CompletableFuture<Void> reload(PreparableReloadListener.@NotNull SharedState state,
                                                   @NotNull Executor prepareExecutor,
                                                   PreparableReloadListener.@NotNull PreparationBarrier barrier,
                                                   @NotNull Executor applyExecutor) {
        return this.delegate.reload(state, prepareExecutor, barrier, applyExecutor);
    }
}
