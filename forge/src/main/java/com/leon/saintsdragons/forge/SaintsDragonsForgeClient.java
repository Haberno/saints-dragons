package com.leon.saintsdragons.forge;

import com.leon.saintsdragons.client.init.CommonClientModEvents;
import com.leon.saintsdragons.client.compat.RealCameraCompatibility;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.common.registry.ModBlocks;
import com.leon.saintsdragons.common.registry.ModBlockEntities;
import com.leon.saintsdragons.forge.client.ForgeDragonUI;
import com.leon.saintsdragons.client.model.block.DraconianNucleusModel;
import com.leon.saintsdragons.client.renderer.block.DraconianNucleusRenderer;
import com.leon.saintsdragons.client.model.block.DraconicCrucibleEntity;
import com.leon.saintsdragons.client.renderer.block.DraconicCrucibleRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SaintsDragonsCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SaintsDragonsForgeClient {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        CommonClientModEvents.registerEntityRenderers(event::registerEntityRenderer);
        event.registerBlockEntityRenderer(ModBlockEntities.DRACONIAN_NUCLEUS.get(), DraconianNucleusRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DRACONIC_CRUCIBLE.get(), DraconicCrucibleRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DraconianNucleusModel.LAYER_LOCATION, DraconianNucleusModel::createBodyLayer);
        event.registerLayerDefinition(DraconicCrucibleEntity.LAYER_LOCATION, DraconicCrucibleEntity::createBodyLayer);
    }

    @SubscribeEvent
    public static void onAddGuiOverlayLayers(AddGuiOverlayLayersEvent event) {
        ForgeDragonUI.registerLayers(event);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RealCameraCompatibility.register();
            CommonClientModEvents.registerMenuScreens();
            // Block render layers are data-driven in 1.21.11; the old runtime setter was removed.
        });
    }
}
