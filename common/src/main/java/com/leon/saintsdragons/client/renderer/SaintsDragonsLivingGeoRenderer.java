package com.leon.saintsdragons.client.renderer;

import com.leon.saintsdragons.client.model.CustomBonePoseModel;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class SaintsDragonsLivingGeoRenderer<T extends LivingEntity & GeoAnimatable>
        extends GeoEntityRenderer<T, SaintsDragonsLivingEntityRenderState> {
    public SaintsDragonsLivingGeoRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public SaintsDragonsLivingEntityRenderState createRenderState(T animatable, @Nullable Void relatedObject) {
        return new SaintsDragonsLivingEntityRenderState();
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject,
                              SaintsDragonsLivingEntityRenderState renderState, float partialTick) {
        renderState.addGeckolibData(GeoRenderDataTickets.ANIMATABLE, animatable);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void adjustModelBonesForRender(RenderPassInfo<SaintsDragonsLivingEntityRenderState> renderPassInfo,
                                          BoneSnapshots snapshots) {
        if (getGeoModel() instanceof CustomBonePoseModel<?> customModel) {
            T animatable = (T) renderPassInfo.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
            if (animatable != null) {
                ((CustomBonePoseModel<T>) customModel).applyCustomBonePose(animatable, renderPassInfo, snapshots);
            }
        }
    }
}
