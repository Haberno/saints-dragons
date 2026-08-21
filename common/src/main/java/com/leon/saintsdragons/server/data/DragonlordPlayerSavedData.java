package com.leon.saintsdragons.server.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DragonlordPlayerSavedData extends SavedData {
    private static final String DATA_NAME = "saintsdragons_dragonlord_players";
    private static final Codec<DragonlordPlayerSavedData> CODEC = SavedHealth.CODEC.listOf()
            .fieldOf("Players")
            .xmap(DragonlordPlayerSavedData::new, DragonlordPlayerSavedData::entries)
            .codec();
    private static final SavedDataType<DragonlordPlayerSavedData> TYPE = new SavedDataType<>(
            DATA_NAME,
            DragonlordPlayerSavedData::new,
            CODEC,
            DataFixTypes.SAVED_DATA_COMMAND_STORAGE
    );
    private final Map<UUID, Float> savedHealth = new HashMap<>();

    public DragonlordPlayerSavedData() {
    }

    private DragonlordPlayerSavedData(List<SavedHealth> entries) {
        entries.forEach(entry -> savedHealth.put(entry.playerId(), entry.health()));
    }

    public static DragonlordPlayerSavedData get(ServerLevel level) {
        ServerLevel storageLevel = level.getServer().overworld();
        return storageLevel.getDataStorage().computeIfAbsent(TYPE);
    }

    public void saveHealth(UUID playerId, float health) {
        if (playerId == null || health <= 0.0F) {
            return;
        }
        savedHealth.put(playerId, health);
        setDirty();
    }

    public Optional<Float> consumeHealth(UUID playerId) {
        if (playerId == null) {
            return Optional.empty();
        }
        Float health = savedHealth.remove(playerId);
        if (health != null) {
            setDirty();
        }
        return Optional.ofNullable(health);
    }

    public Optional<Float> getHealth(UUID playerId) {
        if (playerId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(savedHealth.get(playerId));
    }

    public void clearHealth(UUID playerId) {
        if (playerId != null && savedHealth.remove(playerId) != null) {
            setDirty();
        }
    }

    private List<SavedHealth> entries() {
        return savedHealth.entrySet().stream()
                .map(entry -> new SavedHealth(entry.getKey(), entry.getValue()))
                .toList();
    }

    private record SavedHealth(UUID playerId, float health) {
        private static final Codec<SavedHealth> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                UUIDUtil.CODEC.fieldOf("UUID").forGetter(SavedHealth::playerId),
                Codec.FLOAT.fieldOf("Health").forGetter(SavedHealth::health)
        ).apply(instance, SavedHealth::new));
    }
}
