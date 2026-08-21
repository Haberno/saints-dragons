package com.leon.saintsdragons.client.renderer.item;

import com.leon.saintsdragons.client.model.item.MossbackItemModel;
import com.leon.saintsdragons.common.item.MossbackItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MossbackItemRenderer extends GeoItemRenderer<MossbackItem> {
    public MossbackItemRenderer() {
        super(new MossbackItemModel());
    }
}
