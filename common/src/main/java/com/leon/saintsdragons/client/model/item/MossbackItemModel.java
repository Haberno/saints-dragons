package com.leon.saintsdragons.client.model.item;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.item.MossbackItem;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MossbackItemModel extends GeoModel<MossbackItem> {
    private final boolean baby;

    public MossbackItemModel() {
        this(false);
    }

    public MossbackItemModel(boolean baby) {
        this.baby = baby;
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return SaintsDragonsCommon.rl(baby
                ? "geckolib/models/entity/baby_mossback.geo.json"
                : "geckolib/models/entity/mossback.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return SaintsDragonsCommon.rl(baby
                ? "textures/entity/mossback/baby_mossback.png"
                : "textures/entity/mossback/mossback.png");
    }

    @Override
    public Identifier getAnimationResource(MossbackItem animatable) {
        return SaintsDragonsCommon.rl(baby
                ? "geckolib/animations/entity/baby_mossback.animation.json"
                : "geckolib/animations/entity/mossback.animation.json");
    }
}
