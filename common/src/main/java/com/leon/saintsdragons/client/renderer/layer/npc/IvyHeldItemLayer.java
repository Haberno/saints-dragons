package com.leon.saintsdragons.client.renderer.layer.npc;

import com.leon.saintsdragons.server.entity.npc.IvyTheDragonMerchant;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.cache.model.GeoBone;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;

import java.util.List;

public class IvyHeldItemLayer extends BlockAndItemGeoLayer<IvyTheDragonMerchant, Void, SaintsDragonsLivingEntityRenderState> {
    private static final String RIGHT_HAND_LOCATOR = "rightArmItemLocator";
    private static final DataTicket<ItemStack> HELD_ITEM = DataTicket.create("saintsdragons_ivy_held_item", ItemStack.class);

    public IvyHeldItemLayer(GeoRenderer<IvyTheDragonMerchant, Void, SaintsDragonsLivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(IvyTheDragonMerchant animatable, Void relatedObject,
                              SaintsDragonsLivingEntityRenderState renderState, float partialTick) {
        ItemStack stack = animatable.getRecoveryItemForRender();
        if (stack.isEmpty()) {
            stack = animatable.getSwordForRender();
        }
        renderState.addGeckolibData(HELD_ITEM, stack.copy());
    }

    @Override
    protected List<RenderData<SaintsDragonsLivingEntityRenderState>> getRelevantBones(
            SaintsDragonsLivingEntityRenderState renderState, BakedGeoModel model) {
        return List.of(new RenderData<>(RIGHT_HAND_LOCATOR, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                (bone, state) -> Either.left(state.getGeckolibData(HELD_ITEM))));
    }

    @Override
    protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                         ItemDisplayContext displayContext,
                                         SaintsDragonsLivingEntityRenderState renderState,
                                         SubmitNodeCollector renderTasks, CameraRenderState cameraState,
                                         int packedLight, int packedOverlay, int renderColor) {
        poseStack.pushPose();
        poseStack.translate(0.0D, -0.0625D, -0.1D);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        super.submitItemStackRender(poseStack, bone, stack, displayContext, renderState, renderTasks,
                cameraState, packedLight, packedOverlay, renderColor);
        poseStack.popPose();
    }
}
