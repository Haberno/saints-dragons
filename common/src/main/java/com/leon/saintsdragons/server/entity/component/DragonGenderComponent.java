package com.leon.saintsdragons.server.entity.component;

import com.leon.saintsdragons.server.entity.base.DragonEntity;
import com.leon.saintsdragons.server.entity.base.DragonGender;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;

public final class DragonGenderComponent {
    private final DragonEntity dragon;
    private final EntityDataAccessor<Byte> dataAccessor;
    private boolean genderInitialized = false;

    public DragonGenderComponent(DragonEntity dragon, EntityDataAccessor<Byte> dataAccessor) {
        this.dragon = dragon;
        this.dataAccessor = dataAccessor;
    }

    public DragonGender getGender() {
        return DragonGender.fromId(dragon.getEntityData().get(dataAccessor));
    }

    public void setGender(@Nullable DragonGender gender) {
        DragonGender resolved = gender == null ? DragonGender.MALE : gender;
        dragon.getEntityData().set(dataAccessor, resolved.getId());
        this.genderInitialized = true;
    }

    public boolean isFemale() {
        return getGender() == DragonGender.FEMALE;
    }

    public void setFemale(boolean female) {
        setGender(female ? DragonGender.FEMALE : DragonGender.MALE);
    }

    public boolean hasGender() {
        return genderInitialized;
    }

    public void ensureInitialized() {
        if (dragon.level().isClientSide()) {
            return;
        }
        if (!genderInitialized) {
            setGender(dragon.getRandom().nextBoolean() ? DragonGender.FEMALE : DragonGender.MALE);
        }
    }

    public void saveToNBT(ValueOutput tag) {
        byte genderId = dragon.getEntityData().get(dataAccessor);
        tag.putByte("Gender", genderId);
        tag.putBoolean("IsFemale", genderId == DragonGender.FEMALE.getId());
        tag.putBoolean("GenderInitialized", genderInitialized);
    }

    public void loadFromNBT(ValueInput tag) {
        byte savedGenderId = tag.getByteOr("Gender", (byte)-1);
        if (savedGenderId >= 0) {
            boolean savedGenderInit = tag.getBooleanOr("GenderInitialized", true);
            setGender(DragonGender.fromId(savedGenderId));
            this.genderInitialized = savedGenderInit;
        } else if (tag.read("IsFemale", com.mojang.serialization.Codec.BOOL).isPresent()) {
            setFemale(tag.getBooleanOr("IsFemale", false));
            this.genderInitialized = tag.getBooleanOr("GenderInitialized", true);
        } else {
            this.genderInitialized = false;
            ensureInitialized();
        }
    }

    public boolean isInitialized() {
        return genderInitialized;
    }
}
