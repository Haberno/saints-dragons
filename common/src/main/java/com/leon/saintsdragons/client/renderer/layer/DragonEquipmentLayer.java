package com.leon.saintsdragons.client.renderer.layer;

import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.server.entity.base.DragonEntity;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.renderer.base.GeoRenderer;

import java.util.function.Predicate;

public final class DragonEquipmentLayer<T extends DragonEntity> extends DynamicTextureGeoLayer<T> {
    private final Predicate<T> visible;
    private final Identifier texture;

    public DragonEquipmentLayer(
            GeoRenderer<T, Void, SaintsDragonsLivingEntityRenderState> renderer,
            Predicate<T> visible,
            Identifier texture) {
        super(renderer);
        this.visible = visible;
        this.texture = texture;
    }

    @Override
    protected LayerRenderData getLayerRenderData(T animatable, float partialTick) {
        return visible.test(animatable) ? new LayerRenderData(texture, 0xFFFFFFFF) : null;
    }

    @Override
    protected RenderType getRenderType(Identifier texture) {
        return RenderTypes.entityCutoutNoCull(texture);
    }
}
