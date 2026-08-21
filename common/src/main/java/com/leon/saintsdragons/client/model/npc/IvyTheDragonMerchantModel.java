package com.leon.saintsdragons.client.model.npc;

import com.leon.saintsdragons.client.model.CustomBonePoseModel;
import com.leon.saintsdragons.client.model.LegacyAnimationState;
import com.leon.saintsdragons.client.model.LegacyEntityModelData;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.npc.IvyTheDragonMerchant;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class IvyTheDragonMerchantModel extends DefaultedEntityGeoModel<IvyTheDragonMerchant>
        implements CustomBonePoseModel<IvyTheDragonMerchant> {
    public IvyTheDragonMerchantModel() {
        super(SaintsDragonsCommon.rl("ivy_oleander"));
    }

    @Override
    public void applyCustomBonePose(IvyTheDragonMerchant entity,
                                    RenderPassInfo<? extends GeoRenderState> renderPassInfo,
                                    BoneSnapshots snapshots) {
        LegacyAnimationState<IvyTheDragonMerchant> animationState =
                new LegacyAnimationState<>(renderPassInfo.renderState());
        LegacyEntityModelData modelData = animationState.entityModelData();

        float headYawRad = Mth.clamp(modelData.netHeadYaw(), -45.0F, 45.0F) * Mth.DEG_TO_RAD;
        float headPitchRad = Mth.clamp(modelData.headPitch(), -25.0F, 25.0F) * Mth.DEG_TO_RAD;
        float deviationRad =
                (float) (entity.bodyRotDeviation.get(renderPassInfo.renderState().getPartialTick()) * Mth.DEG_TO_RAD);

        BoneSnapshot head = snapshots.get("head").orElse(null);
        if (head != null && entity.shouldApplyHeadTracking()) {
            head.setRotY(head.getRotY() + headYawRad);
            head.setRotX(head.getRotX() + headPitchRad);
        }

        BoneSnapshot body = snapshots.get("waist").orElse(null);
        if (body == null) {
            body = snapshots.get("body").orElse(null);
        }
        if (body != null) {
            body.setRotY(body.getRotY() - deviationRad);
        }
    }
}
