package com.leon.saintsdragons.client.model.draconianswarm;

import com.leon.saintsdragons.client.model.LegacyAnimationState;
import com.leon.saintsdragons.client.model.LegacyEntityGeoModel;
import com.leon.saintsdragons.client.model.LegacyEntityModelData;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.draconianswarm.Winged;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class WingedModel extends LegacyEntityGeoModel<Winged> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("entity/winged");
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/draconian_swarm/winged/winged.png");
    private static final Identifier ANIMATIONS =
            SaintsDragonsCommon.rl("entity/winged");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(Winged animatable) {
        return ANIMATIONS;
    }

    @Override
    protected void setCustomAnimations(Winged entity, long instanceId, LegacyAnimationState<Winged> animationState) {
        if (!entity.isAlive()) {
            return;
        }

        float pitch = Mth.clamp(entity.getFlightPitchRadians(animationState.renderState().getPartialTick()), -0.95F, 0.95F);
        getBone("root").ifPresent(bone -> bone.setRotX(bone.getRotX() + pitch));

        LegacyEntityModelData modelData = animationState.entityModelData();
        float lookPitch = Mth.clamp(modelData.headPitch() * Mth.DEG_TO_RAD, -0.55F, 0.55F);
        float lookYaw = Mth.clamp(modelData.netHeadYaw() * Mth.DEG_TO_RAD, -0.75F, 0.75F);
        getBone("head").ifPresent(bone -> {
            bone.setRotX(bone.getRotX() + lookPitch);
            bone.setRotY(bone.getRotY() + lookYaw);
        });
    }
}
