package com.leon.saintsdragons.server.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WikiReminderSavedData extends SavedData {
    private static final String DATA_NAME = "saintsdragons_wiki_reminders";
    private static final Codec<WikiReminderSavedData> CODEC = SavedPlayer.CODEC.listOf()
            .fieldOf("Players")
            .xmap(WikiReminderSavedData::new, WikiReminderSavedData::entries)
            .codec();
    private static final SavedDataType<WikiReminderSavedData> TYPE = new SavedDataType<>(
            DATA_NAME,
            WikiReminderSavedData::new,
            CODEC,
            DataFixTypes.SAVED_DATA_COMMAND_STORAGE
    );
    private final Set<UUID> shownPlayers = new HashSet<>();

    public WikiReminderSavedData() {
    }

    private WikiReminderSavedData(List<SavedPlayer> entries) {
        entries.forEach(entry -> shownPlayers.add(entry.playerId()));
    }

    public static WikiReminderSavedData get(ServerLevel level) {
        ServerLevel storageLevel = level.getServer().overworld();
        return storageLevel.getDataStorage().computeIfAbsent(TYPE);
    }

    public boolean markShownIfFirst(UUID playerId) {
        boolean added = shownPlayers.add(playerId);
        if (added) {
            setDirty();
        }
        return added;
    }

    private List<SavedPlayer> entries() {
        return shownPlayers.stream().map(SavedPlayer::new).toList();
    }

    private record SavedPlayer(UUID playerId) {
        private static final Codec<SavedPlayer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                UUIDUtil.CODEC.fieldOf("UUID").forGetter(SavedPlayer::playerId)
        ).apply(instance, SavedPlayer::new));
    }
}
