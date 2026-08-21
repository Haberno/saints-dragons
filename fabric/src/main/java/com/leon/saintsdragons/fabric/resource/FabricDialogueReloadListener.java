package com.leon.saintsdragons.fabric.resource;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.npc.dialogue.DialogueReloadListener;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class FabricDialogueReloadListener implements IdentifiableResourceReloadListener {
    private static final Identifier ID = SaintsDragonsCommon.rl("dialogues");
    private final DialogueReloadListener delegate = DialogueReloadListener.getInstance();

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
