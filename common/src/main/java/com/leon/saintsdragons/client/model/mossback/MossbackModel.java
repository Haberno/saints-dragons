package com.leon.saintsdragons.client.model.mossback;

import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.dragons.Mossback;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MossbackModel extends DefaultedEntityGeoModel<Mossback> {
    private static final Identifier ADULT_MODEL =
            SaintsDragonsCommon.rl("entity/mossback");
    private static final Identifier BABY_MODEL =
            SaintsDragonsCommon.rl("entity/baby_mossback");
    private static final Identifier ADULT_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/mossback/mossback.png");
    private static final Identifier BABY_TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/mossback/baby_mossback.png");
    private static final Identifier ADULT_ANIMATION =
            SaintsDragonsCommon.rl("entity/mossback");
    private static final Identifier BABY_ANIMATION =
            SaintsDragonsCommon.rl("entity/baby_mossback");

    public MossbackModel() {
        super(SaintsDragonsCommon.rl("mossback"));
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        Mossback entity = getAnimatable(renderState);
        return entity != null && entity.isBaby() ? BABY_MODEL : ADULT_MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        Mossback entity = getAnimatable(renderState);
        return entity != null && entity.isBaby() ? BABY_TEXTURE : ADULT_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(Mossback entity) {
        return entity != null && entity.isBaby() ? BABY_ANIMATION : ADULT_ANIMATION;
    }

    @Override
    public void addAdditionalStateData(Mossback animatable, Object relatedObject, GeoRenderState renderState) {
        renderState.addGeckolibData(GeoRenderDataTickets.ANIMATABLE, animatable);
    }

    private static Mossback getAnimatable(GeoRenderState renderState) {
        return (Mossback) renderState.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
    }
}
