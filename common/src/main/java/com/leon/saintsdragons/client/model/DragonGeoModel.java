package com.leon.saintsdragons.client.model;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import com.leon.saintsdragons.server.entity.base.DragonEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class DragonGeoModel<T extends DragonEntity> extends DefaultedEntityGeoModel<T>
        implements CustomBonePoseModel<T> {
    protected final Identifier model;
    protected final Identifier babyModel;
    protected final Identifier animation;
    protected final Identifier babyAnimation;
    protected final Identifier maleTexture;
    protected final Identifier femaleTexture;
    protected final Identifier babyMaleTexture;
    protected final Identifier babyFemaleTexture;
    private BoneSnapshots currentSnapshots;

    protected DragonGeoModel(String dragonId) {
        this(dragonId, true);
    }

    protected DragonGeoModel(String dragonId, boolean hasBabyResources) {
        super(SaintsDragonsCommon.rl(dragonId));
        this.model = SaintsDragonsCommon.rl("geckolib/models/entity/" + dragonId + ".geo.json");
        this.animation = SaintsDragonsCommon.rl("geckolib/animations/entity/" + dragonId + ".animation.json");
        this.maleTexture = SaintsDragonsCommon.rl("textures/entity/" + dragonId + "/" + dragonId + ".png");
        this.femaleTexture = SaintsDragonsCommon.rl("textures/entity/" + dragonId + "/" + dragonId + "_female.png");
        if (hasBabyResources) {
            this.babyModel = SaintsDragonsCommon.rl("geckolib/models/entity/baby_" + dragonId + ".geo.json");
            this.babyAnimation = SaintsDragonsCommon.rl("geckolib/animations/entity/baby_" + dragonId + ".animation.json");
            this.babyMaleTexture = SaintsDragonsCommon.rl("textures/entity/" + dragonId + "/baby_" + dragonId + ".png");
            this.babyFemaleTexture = SaintsDragonsCommon.rl("textures/entity/" + dragonId + "/baby_" + dragonId + "_female.png");
        } else {
            this.babyModel = this.model;
            this.babyAnimation = this.animation;
            this.babyMaleTexture = this.maleTexture;
            this.babyFemaleTexture = this.femaleTexture;
        }
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        T entity = getAnimatable(renderState);
        return entity != null && entity.isBaby() ? babyModel : model;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        T entity = getAnimatable(renderState);
        if (entity == null) {
            return maleTexture;
        }
        if (entity.isBaby()) {
            return getBabyTexture(entity);
        }
        return getAdultTexture(entity);
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return entity != null && entity.isBaby() ? babyAnimation : animation;
    }

    protected Identifier getAdultTexture(T entity) {
        if (entity.hasCustomTextureVariant()) {
            return entity.getCustomAdultTextureResource(entity.isFemale());
        }
        return entity.isFemale() ? femaleTexture : maleTexture;
    }

    protected Identifier getBabyTexture(T entity) {
        return entity.isFemale() ? babyFemaleTexture : babyMaleTexture;
    }

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        renderState.addGeckolibData(GeoRenderDataTickets.ANIMATABLE, animatable);
    }

    @SuppressWarnings("unchecked")
    private T getAnimatable(GeoRenderState renderState) {
        return (T) renderState.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
    }

    public Optional<BoneSnapshot> getBone(String boneName) {
        return currentSnapshots == null ? Optional.empty() : currentSnapshots.get(boneName);
    }

    public void setCustomAnimations(T entity, long instanceId, LegacyAnimationState<T> animationState) {
    }

    @Override
    public final void applyCustomBonePose(T entity, RenderPassInfo<? extends GeoRenderState> renderPassInfo,
                                          BoneSnapshots snapshots) {
        currentSnapshots = snapshots;
        try {
            setCustomAnimations(entity, entity.getId(), new LegacyAnimationState<>(renderPassInfo.renderState()));
        } finally {
            currentSnapshots = null;
        }
    }
}
