package com.leon.saintsdragons.client.renderer.layer;

import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.server.entity.base.DragonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.concurrent.atomic.AtomicInteger;

/** Shared GeckoLib 5 deferred-render implementation for full-model texture overlays. */
public abstract class DynamicTextureGeoLayer<T extends DragonEntity>
        extends GeoRenderLayer<T, Void, SaintsDragonsLivingEntityRenderState> {
    private static final AtomicInteger NEXT_TICKET_ID = new AtomicInteger();
    private final DataTicket<LayerRenderData> layerDataTicket = DataTicket.create(
            "saintsdragons_dynamic_layer_" + NEXT_TICKET_ID.getAndIncrement(), LayerRenderData.class);

    protected DynamicTextureGeoLayer(
            GeoRenderer<T, Void, SaintsDragonsLivingEntityRenderState> renderer) {
        super(renderer);
    }

    @Override
    public final void addRenderData(T animatable, Void relatedObject,
                                    SaintsDragonsLivingEntityRenderState renderState, float partialTick) {
        LayerRenderData data = getLayerRenderData(animatable, partialTick);
        if (data != null) {
            renderState.addGeckolibData(layerDataTicket, data);
        }
    }

    protected abstract @Nullable LayerRenderData getLayerRenderData(T animatable, float partialTick);

    protected RenderType getRenderType(Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    @Override
    public final void submitRenderTask(RenderPassInfo<SaintsDragonsLivingEntityRenderState> renderPassInfo,
                                       SubmitNodeCollector renderTasks) {
        LayerRenderData data = renderPassInfo.getGeckolibData(layerDataTicket);
        if (data == null || !renderPassInfo.willRender()) {
            return;
        }

        int packedLight = data.packedLight() < 0 ? renderPassInfo.packedLight() : data.packedLight();
        RenderType renderType = getRenderType(data.texture());
        renderTasks.order(1).submitCustomGeometry(renderPassInfo.poseStack(), renderType, (pose, vertexConsumer) -> {
            PoseStack poseStack = renderPassInfo.poseStack();
            poseStack.pushPose();
            poseStack.last().set(pose);
            renderPassInfo.renderPosed(() -> renderPassInfo.model().render(
                    renderPassInfo, vertexConsumer, packedLight, renderPassInfo.packedOverlay(), data.argbColor()));
            poseStack.popPose();
        });
    }

    protected record LayerRenderData(Identifier texture, int argbColor, int packedLight) {
        public LayerRenderData {
        }

        public LayerRenderData(Identifier texture, int argbColor) {
            this(texture, argbColor, -1);
        }
    }
}
