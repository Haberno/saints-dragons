package com.leon.saintsdragons.client.renderer.armor;

import com.leon.saintsdragons.client.model.armor.BloodTempestArmorModel;
import com.leon.saintsdragons.common.item.BloodTempestArmorItem;
import com.leon.saintsdragons.client.renderer.vfx.BloodTempestAfterimageRenderContext;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsHumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib.renderer.GeoArmorRenderer;


public class BloodTempestArmorRenderer extends GeoArmorRenderer<BloodTempestArmorItem, SaintsDragonsHumanoidRenderState> {
    public BloodTempestArmorRenderer() {
        super(new BloodTempestArmorModel());
    }

    @Override
    public String getBoneNameForSegment(SaintsDragonsHumanoidRenderState renderState, ArmorSegment segment) {
        return segment == ArmorSegment.HEAD ? "armorhead" : super.getBoneNameForSegment(renderState, segment);
    }

    @Override
    public RenderType getRenderType(SaintsDragonsHumanoidRenderState renderState, Identifier texture) {
        if (BloodTempestAfterimageRenderContext.isActive()) {
            return RenderTypes.entityTranslucent(texture);
        }
        return super.getRenderType(renderState, texture);
    }

    @Override
    public int getRenderColor(BloodTempestArmorItem animatable, RenderData renderData, float partialTick) {
        if (BloodTempestAfterimageRenderContext.isActive()) {
            return ARGB.colorFromFloat(
                    BloodTempestAfterimageRenderContext.alpha(),
                    BloodTempestAfterimageRenderContext.red(),
                    BloodTempestAfterimageRenderContext.green(),
                    BloodTempestAfterimageRenderContext.blue()
            );
        }
        return super.getRenderColor(animatable, renderData, partialTick);
    }

    @Override
    public SaintsDragonsHumanoidRenderState createRenderState(BloodTempestArmorItem animatable, RenderData renderData) {
        return new SaintsDragonsHumanoidRenderState();
    }
}
