package com.leon.saintsdragons.client.renderer.armor;

import com.leon.saintsdragons.client.model.armor.DragonlordArmorModel;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsHumanoidRenderState;
import com.leon.saintsdragons.common.item.DragonlordArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class DragonlordArmorRenderer extends GeoArmorRenderer<DragonlordArmorItem, SaintsDragonsHumanoidRenderState> {
    public DragonlordArmorRenderer() {
        super(new DragonlordArmorModel());
    }

    @Override
    public SaintsDragonsHumanoidRenderState createRenderState(DragonlordArmorItem animatable, RenderData renderData) {
        return new SaintsDragonsHumanoidRenderState();
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<SaintsDragonsHumanoidRenderState> renderPassInfo,
                                          BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        if (renderPassInfo.getGeckolibData(com.leon.saintsdragons.client.renderer.GeoRenderDataTickets.ARMOR_WEARER)
                instanceof net.minecraft.world.entity.LivingEntity wearer) {
            ((DragonlordArmorModel) getGeoModel()).applyCustomBonePose(
                    wearer, renderPassInfo.renderState().getPartialTick(), snapshots);
        }
    }
}
