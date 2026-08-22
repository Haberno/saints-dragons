package com.leon.saintsdragons.client.model.armor;

import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import com.leon.saintsdragons.common.item.DragonlordArmorItem;
import com.leon.saintsdragons.common.item.DragonlordArmorSetBonus;
import com.leon.saintsdragons.server.flight.DragonFlightVisuals;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Map;
import java.util.WeakHashMap;

public class DragonlordArmorModel extends GeoModel<DragonlordArmorItem> {
    private static final float DEG_TO_RAD = Mth.DEG_TO_RAD;
    private static final Identifier MODEL = SaintsDragonsCommon.rl("armor/dragonlord_armor");
    private static final Identifier TEXTURE = SaintsDragonsCommon.rl("textures/armor/dragonlord_armor.png");
    private static final Identifier ANIMATION = SaintsDragonsCommon.rl("armor/dragonlord_armor");
    private final Map<LivingEntity, DivePoseTracker> divePoseTrackers = new WeakHashMap<>();

    public void applyCustomBonePose(LivingEntity living, float partialTick, BoneSnapshots snapshots) {
        if (!living.isFallFlying()
                || !DragonlordArmorSetBonus.isWearingFullSet(living)) {
            divePoseTrackers.remove(living);
            return;
        }

        float blend = getDivePose(living, partialTick);
        if (blend <= 0.001F) {
            return;
        }

        applyDiveRotation(snapshots, "leftwing", blend, -4.49F, -3.48F, 22.68F);
        applyDiveRotation(snapshots, "leftforewing", blend, -0.08F, 62.5F, -0.7F);
        applyDiveRotation(snapshots, "leftfinger1", blend, 3.5F, -57.66F, 7.3F);
        applyDiveRotation(snapshots, "leftfinger2", blend, 0.0F, -25.0F, 0.0F);
        applyDiveRotation(snapshots, "leftfinger3", blend, 0.0F, -27.5F, 0.0F);

        applyDiveRotation(snapshots, "rightwing", blend, -4.49F, 3.48F, -22.68F);
        applyDiveRotation(snapshots, "rightforewing", blend, -0.08F, -62.5F, 0.7F);
        applyDiveRotation(snapshots, "rightfinger1", blend, 3.5F, 57.66F, -7.3F);
        applyDiveRotation(snapshots, "rightfinger2", blend, 0.0F, 25.0F, 0.0F);
        applyDiveRotation(snapshots, "rightfinger3", blend, 0.0F, 27.5F, 0.0F);
    }

    private float getDivePose(LivingEntity living, float partialTick) {
        DivePoseTracker tracker = divePoseTrackers.computeIfAbsent(
                living,
                ignored -> new DivePoseTracker(living.tickCount - 1)
        );

        int elapsedTicks = living.tickCount - tracker.lastTick;
        if (elapsedTicks < 0) {
            tracker.pose = new DragonFlightVisuals.DivePoseState();
            elapsedTicks = 1;
        }

        int updates = Mth.clamp(elapsedTicks, 0, 5);
        for (int i = 0; i < updates; i++) {
            DragonFlightVisuals.tickDivePose(tracker.pose, true, living.getDeltaMovement());
        }
        tracker.lastTick = living.tickCount;

        return Mth.clamp(DragonFlightVisuals.getDivePose(tracker.pose, partialTick), 0.0F, 1.0F);
    }

    private void applyDiveRotation(BoneSnapshots snapshots, String boneName, float blend,
                                   float xDegrees, float yDegrees, float zDegrees) {
        snapshots.get(boneName).ifPresent(bone -> {
            // GeckoLib converts Blockbench rotations with inverted X/Y axes.
            bone.setRotX(bone.getRotX() - xDegrees * DEG_TO_RAD * blend);
            bone.setRotY(bone.getRotY() - yDegrees * DEG_TO_RAD * blend);
            bone.setRotZ(bone.getRotZ() + zDegrees * DEG_TO_RAD * blend);
        });
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public void addAdditionalStateData(DragonlordArmorItem animatable, Object relatedObject, GeoRenderState renderState) {
        if (relatedObject instanceof GeoArmorRenderer.RenderData renderData) {
            renderState.addGeckolibData(GeoRenderDataTickets.ARMOR_WEARER, renderData.entity());
        }
    }

    @Override
    public Identifier getAnimationResource(DragonlordArmorItem animatable) {
        return ANIMATION;
    }

    private static final class DivePoseTracker {
        private DragonFlightVisuals.DivePoseState pose = new DragonFlightVisuals.DivePoseState();
        private int lastTick;

        private DivePoseTracker(int lastTick) {
            this.lastTick = lastTick;
        }
    }
}
