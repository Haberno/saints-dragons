package com.leon.saintsdragons.client.model.ignivorus;

import com.leon.saintsdragons.client.model.DragonGeoModel;
import com.leon.saintsdragons.client.model.DragonModelPoseHelper;
import com.leon.saintsdragons.client.model.DragonModelPoseHelper.WeightedBoneChain;
import com.leon.saintsdragons.client.ui.DraconicCodexScreen;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.constant.DataTickets;
import com.leon.saintsdragons.client.model.LegacyAnimationState;
import com.leon.saintsdragons.client.model.LegacyEntityModelData;

public class IgnivorusModel extends DragonGeoModel<Ignivorus> {
    private static final float DEG_TO_RAD = Mth.DEG_TO_RAD;
    private static final WeightedBoneChain NECK = WeightedBoneChain.of(
            new String[] {"neck1Controller", "neck2Controller", "neck3Controller", "neck4Controller", "headController"},
            0.40f, 0.41f, 0.42f, 0.43f, 0.44f
    );
    private static final WeightedBoneChain NECK_FOLLOW = WeightedBoneChain.of(
            new String[] {"neck1Controller", "neck2Controller", "neck3Controller", "neck4Controller", "headController"},
            0.30f, 0.35f, 0.40f, 0.42f, 0.45f
    );
    private static final WeightedBoneChain TAIL = WeightedBoneChain.of(
            new String[] {"tail1", "tail2", "tail3", "tail4"},
            0.5f, 0.75f, 1.0f, 1.25f
    );

    public IgnivorusModel() {
        super("ignivorus");
    }

    private static final Identifier CRIMSON_TEXTURE = SaintsDragonsCommon.rl("textures/entity/ignivorus/crimson_ignivorus.png");
    private static final Identifier CRIMSON_FEMALE_TEXTURE = SaintsDragonsCommon.rl("textures/entity/ignivorus/crimson_ignivorus_female.png");

    @Override
    protected Identifier getAdultTexture(Ignivorus entity) {
        if (entity.hasCustomTextureVariant()) {
            return super.getAdultTexture(entity);
        }
        if (entity.getTextureVariant() == Ignivorus.VARIANT_CRIMSON) {
            return entity.isFemale() ? CRIMSON_FEMALE_TEXTURE : CRIMSON_TEXTURE;
        }
        return super.getAdultTexture(entity);
    }

    @Override
    public void setCustomAnimations(Ignivorus entity, long instanceId, LegacyAnimationState<Ignivorus> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);

        if (DraconicCodexScreen.RENDERING_IN_GUI.get()) {
            return;
        }
        if (entity.isScentAssessing()) {
            return;
        }
        LegacyEntityModelData modelData = animationState.entityModelData();
        if (modelData == null) return;

        float partialTick = animationState.renderState().getPartialTick();

        if (entity.isAlive()) {
            if (entity.isDeadOrDying()){
                return;
            }
            if (!entity.isVehicle() && !entity.isInWater()) {
                applyNeckFollow(entity, modelData, animationState.renderState().getPartialTick());
            }
            applyBodyRotationDeviation(entity, partialTick);
            applyBankingRoll(entity, animationState);
            applyFlightPitch(entity, animationState);
            applyDiveWingPose(entity, partialTick);
            applyNeckBankingLean(entity, partialTick);
            applyGroundNeckTurn(entity, partialTick);
            applyTailDrag(entity, partialTick);
        }
    }

    private void applyBodyRotationDeviation(Ignivorus entity, float partialTick) {
        DragonModelPoseHelper.applyBodyYawDeviation(this, entity, "root", partialTick, -1.0f, true);
    }

    private void applyBankingRoll(Ignivorus entity, LegacyAnimationState<Ignivorus> state) {
        var bodyOpt = getBone("body");
        if (bodyOpt.isEmpty()) return;

        BoneSnapshot body = bodyOpt.get();
        var snap = body;
        float partialTick = state.renderState().getPartialTick();
        float bankAngleDeg = entity.getBankAngleDegrees(partialTick);
        float bankAngleRad = Mth.clamp(-bankAngleDeg * Mth.DEG_TO_RAD, -Mth.HALF_PI, Mth.HALF_PI);
        float barrelRollRad = entity.getSmoothedRoll(partialTick);
        body.setRotZ(snap.getRotZ() + bankAngleRad + barrelRollRad);
    }

    private void applyFlightPitch(Ignivorus entity, LegacyAnimationState<Ignivorus> state) {
        var rootOpt = getBone("root");
        if (rootOpt.isEmpty()) return;

        BoneSnapshot root = rootOpt.get();
        var snap = root;

        float partialTick = state.renderState().getPartialTick();
        float pitchRad = entity.getFlightPitchRadians(partialTick);
        pitchRad = Mth.clamp(pitchRad, -Mth.HALF_PI, Mth.HALF_PI);

        root.setRotX(snap.getRotX() + pitchRad);
    }

    private void applyDiveWingPose(Ignivorus entity, float partialTick) {
        if (entity.isInWater()) {
            return;
        }

        float blend = Mth.clamp(entity.getDivePose(partialTick), 0.0F, 1.0F);
        if (blend <= 0.001F) {
            return;
        }

        applyDiveRotation("leftwing", blend, 0.0F, -30.0F, 8.5F);
        applyDiveRotation("leftwingjoint", blend, 0.0F, 77.5F, -7.5F);
        applyDiveRotation("leftinnerphalanges", blend, -1.833F, -60.015F, -2.5F);
        applyDiveRotation("leftphalanges", blend, 0.0F, 0.0F, 0.0F);
        applyDiveRotation("leftmiddlephalanges", blend, 0.0F, -12.5F, 0.0F);
        applyDiveRotation("leftouterphalanges", blend, 2.5F, -12.5F, 0.0F);

        applyDiveRotation("rightwing", blend, 0.0F, 30.0F, -8.5F);
        applyDiveRotation("rightwingjoint", blend, 0.0F, -77.5F, 7.5F);
        applyDiveRotation("rightinnerphalanges", blend, -1.833F, 60.015F, 2.5F);
        applyDiveRotation("rightphalanges", blend, 0.0F, 0.0F, 0.0F);
        applyDiveRotation("rightmiddlephalanges", blend, 0.0F, 12.5F, 0.0F);
        applyDiveRotation("rightouterphalanges", blend, 2.5F, 12.5F, 0.0F);
    }

    private void applyDiveRotation(String boneName, float blend, float xDegrees, float yDegrees, float zDegrees) {
        getBone(boneName).ifPresent(bone -> {
            bone.setRotX(bone.getRotX() - xDegrees * DEG_TO_RAD * blend);
            bone.setRotY(bone.getRotY() - yDegrees * DEG_TO_RAD * blend);
            bone.setRotZ(bone.getRotZ() + zDegrees * DEG_TO_RAD * blend);
        });
    }

    private void applyNeckBankingLean(Ignivorus entity, float partialTick) {
        if (!entity.isVehicle() || !entity.isFlying()) {
            return;
        }
        float bankAngleDeg = entity.getBankAngleDegrees(partialTick);
        float neckLeanRad = -(bankAngleDeg / 45.0f) * 32.0f * Mth.DEG_TO_RAD;
        DragonModelPoseHelper.applyWeightedRotationY(this, NECK, neckLeanRad);
    }

    private void applyGroundNeckTurn(Ignivorus entity, float partialTick) {
        if (entity.isFlying()) {
            return;
        }
        DragonModelPoseHelper.applyGroundNeckTurn(this, entity, partialTick, NECK, 25.0);
    }

    private void applyNeckFollow(Ignivorus entity, LegacyEntityModelData modelData, float partialTick) {
        float totalYawRad = DragonModelPoseHelper.lookYawWithBodyDeviation(entity, modelData, partialTick, 2.0);
        float lookPitchRad = modelData.headPitch() * Mth.DEG_TO_RAD;
        if (entity.isFlying()) {
            lookPitchRad *= 0.5f;
        }

        DragonModelPoseHelper.applyWeightedNeckFollow(this, entity, NECK_FOLLOW, lookPitchRad, totalYawRad);
    }

    private void applyTailDrag(Ignivorus entity, float partialTick) {
        DragonModelPoseHelper.applyTailDrag(this, entity, partialTick, TAIL, 30.0);
    }
}
