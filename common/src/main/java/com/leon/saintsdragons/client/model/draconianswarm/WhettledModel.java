package com.leon.saintsdragons.client.model.draconianswarm;

import com.leon.saintsdragons.client.model.LegacyAnimationState;
import com.leon.saintsdragons.client.model.LegacyEntityGeoModel;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.draconianswarm.Whettled;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class WhettledModel extends LegacyEntityGeoModel<Whettled> {
    private static final Identifier MODEL = SaintsDragonsCommon.rl("entity/whettled");
    private static final Identifier TEXTURE =
            SaintsDragonsCommon.rl("textures/entity/draconian_swarm/whettled/whettled.png");
    private static final Identifier ANIMATIONS =
            SaintsDragonsCommon.rl("entity/whettled");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(Whettled animatable) {
        return ANIMATIONS;
    }

    @Override
    protected void setCustomAnimations(Whettled entity, long instanceId, LegacyAnimationState<Whettled> animationState) {
        if (!entity.isAlive()) {
            return;
        }

        float partialTick = animationState.renderState().getPartialTick();
        float pitch = Mth.clamp(entity.getFlightPitchRadians(partialTick), -0.95F, 0.95F);
        float drag = Mth.clamp(entity.getTailDragYawRadians(partialTick), -0.95F, 0.95F);
        getBone("root").ifPresent(bone -> bone.setRotX(bone.getRotX() + pitch));
        applyTailDrag("tail1rot", drag * 0.70F);
        applyTailDrag("tail2rot", drag);
        applyTailDrag("tail3rot", drag * 1.30F);
    }

    private void applyTailDrag(String boneName, float yaw) {
        getBone(boneName).ifPresent(bone -> bone.setRotY(bone.getRotY() + yaw));
    }
}
