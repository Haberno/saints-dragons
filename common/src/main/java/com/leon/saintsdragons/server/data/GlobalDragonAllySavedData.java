package com.leon.saintsdragons.server.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.util.datafix.DataFixTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Persistent per-player ally registry shared across all dragons.
 */
public class GlobalDragonAllySavedData extends SavedData {
    private static final String DATA_NAME = "saintsdragons_global_allies";
    private static final SavedDataType<GlobalDragonAllySavedData> TYPE = new SavedDataType<>(
            DATA_NAME,
            GlobalDragonAllySavedData::new,
            CompoundTag.CODEC.xmap(GlobalDragonAllySavedData::load, GlobalDragonAllySavedData::save),
            DataFixTypes.SAVED_DATA_COMMAND_STORAGE
    );

    private final Map<UUID, Map<UUID, String>> alliesByOwner = new HashMap<>();

    public static GlobalDragonAllySavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public Map<UUID, String> getAllies(UUID ownerId) {
        Map<UUID, String> allies = alliesByOwner.get(ownerId);
        if (allies == null) {
            return Map.of();
        }
        return new HashMap<>(allies);
    }

    public boolean isAlly(UUID ownerId, UUID allyId) {
        Map<UUID, String> allies = alliesByOwner.get(ownerId);
        if (allies == null) {
            return false;
        }
        return allies.containsKey(allyId);
    }

    public int getAllyCount(UUID ownerId) {
        Map<UUID, String> allies = alliesByOwner.get(ownerId);
        return allies == null ? 0 : allies.size();
    }

    public void addAlly(UUID ownerId, UUID allyId, String username) {
        alliesByOwner.computeIfAbsent(ownerId, id -> new HashMap<>()).put(allyId, username);
        setDirty();
    }

    public boolean removeAlly(UUID ownerId, UUID allyId) {
        Map<UUID, String> allies = alliesByOwner.get(ownerId);
        if (allies == null) {
            return false;
        }
        boolean removed = allies.remove(allyId) != null;
        if (removed) {
            setDirty();
        }
        return removed;
    }

    public void clearAllies(UUID ownerId) {
        Map<UUID, String> allies = alliesByOwner.get(ownerId);
        if (allies != null && !allies.isEmpty()) {
            allies.clear();
            setDirty();
        }
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        ListTag playerList = new ListTag();
        for (Map.Entry<UUID, Map<UUID, String>> entry : alliesByOwner.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.store("Owner", UUIDUtil.CODEC, entry.getKey());
            ListTag allyList = new ListTag();
            for (Map.Entry<UUID, String> allyEntry : entry.getValue().entrySet()) {
                CompoundTag allyTag = new CompoundTag();
                allyTag.store("UUID", UUIDUtil.CODEC, allyEntry.getKey());
                allyTag.putString("Username", allyEntry.getValue());
                allyList.add(allyTag);
            }
            playerTag.put("Allies", allyList);
            playerList.add(playerTag);
        }
        tag.put("Players", playerList);
        return tag;
    }

    public static GlobalDragonAllySavedData load(CompoundTag tag) {
        GlobalDragonAllySavedData data = new GlobalDragonAllySavedData();
        if (tag.contains("Players")) {
            ListTag playerList = tag.getListOrEmpty("Players");
            for (int i = 0; i < playerList.size(); i++) {
                CompoundTag playerTag = playerList.getCompoundOrEmpty(i);
                UUID ownerId = playerTag.read("Owner", UUIDUtil.CODEC).orElse(null);
                if (ownerId == null) {
                    continue;
                }
                Map<UUID, String> allies = new HashMap<>();
                if (playerTag.contains("Allies")) {
                    ListTag allyList = playerTag.getListOrEmpty("Allies");
                    for (int j = 0; j < allyList.size(); j++) {
                        CompoundTag allyTag = allyList.getCompoundOrEmpty(j);
                        UUID allyId = allyTag.read("UUID", UUIDUtil.CODEC).orElse(null);
                        String username = allyTag.getStringOr("Username", "");
                        if (allyId != null && !username.isEmpty()) {
                            allies.put(allyId, username);
                        }
                    }
                }
                data.alliesByOwner.put(ownerId, allies);
            }
        }
        return data;
    }
}
