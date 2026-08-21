package com.leon.saintsdragons.client.renderer.volitans;

import com.leon.saintsdragons.client.model.volitans.VolitansSpineModel;
import com.leon.saintsdragons.client.renderer.SaintsDragonsEntityGeoRenderer;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsEntityRenderState;
import com.leon.saintsdragons.server.entity.effect.volitans.VolitansSpineEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class VolitansSpineRenderer extends SaintsDragonsEntityGeoRenderer<VolitansSpineEntity> {
    public VolitansSpineRenderer(EntityRendererProvider.Context context) {
        super(context, new VolitansSpineModel());
        this.shadowRadius = 0.15F;
    }

    @Override
    protected void applyRotations(RenderPassInfo<SaintsDragonsEntityRenderState> renderPassInfo,
                                  PoseStack poseStack, float nativeScale) {
        float yaw = renderPassInfo.getOrDefaultGeckolibData(DataTickets.ENTITY_YAW, 0.0F);
        float pitch = renderPassInfo.getOrDefaultGeckolibData(DataTickets.ENTITY_PITCH, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }
}
