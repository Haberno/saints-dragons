package com.leon.saintsdragons.client.renderer;

import com.leon.saintsdragons.client.model.CustomBonePoseModel;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class SaintsDragonsEntityGeoRenderer<T extends Entity & GeoAnimatable>
        extends GeoEntityRenderer<T, SaintsDragonsEntityRenderState> {
    public SaintsDragonsEntityGeoRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public SaintsDragonsEntityRenderState createRenderState(T animatable, @Nullable Void relatedObject) {
        return new SaintsDragonsEntityRenderState();
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject,
                              SaintsDragonsEntityRenderState renderState, float partialTick) {
        renderState.addGeckolibData(GeoRenderDataTickets.ANIMATABLE, animatable);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void adjustModelBonesForRender(RenderPassInfo<SaintsDragonsEntityRenderState> renderPassInfo,
                                          BoneSnapshots snapshots) {
        if (getGeoModel() instanceof CustomBonePoseModel<?> customModel) {
            T animatable = (T) renderPassInfo.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
            if (animatable != null) {
                ((CustomBonePoseModel<T>) customModel).applyCustomBonePose(animatable, renderPassInfo, snapshots);
            }
        }
    }
}
