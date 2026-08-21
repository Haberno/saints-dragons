package com.leon.saintsdragons.forge.client;

import com.leon.saintsdragons.client.debug.DragonBrainDebugHud;
import com.leon.saintsdragons.client.ui.DragonRideHealthBar;
import com.leon.saintsdragons.client.ui.DragonUIRegistry;
import com.leon.saintsdragons.client.ui.FireballChargeIndicator;
import com.leon.saintsdragons.client.ui.IgnivorusFireBreathMeterIndicator;
import com.leon.saintsdragons.client.ui.MeleeModeNotification;
import com.leon.saintsdragons.client.ui.RaevyxBeamMeterIndicator;
import com.leon.saintsdragons.client.ui.SpeedLineOverlay;
import com.leon.saintsdragons.client.ui.SwarmWaveBarOverlay;
import com.leon.saintsdragons.client.ui.VolitansBreathMeterIndicator;
import com.leon.saintsdragons.common.SaintsDragonsCommon;
import com.leon.saintsdragons.forge.client.event.DragonUIKeybinds;
import com.leon.saintsdragons.forge.platform.ForgeClientConfig;
import com.leon.saintsdragons.server.entity.base.DragonEntity;
import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import com.leon.saintsdragons.server.entity.dragons.raevyx.Raevyx;
import com.leon.saintsdragons.server.entity.dragons.volitans.Volitans;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Forge 1.21.11 wiring for the shared dragon HUD. */
@Mod.EventBusSubscriber(modid = SaintsDragonsCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ForgeDragonUI {
    private static final MeleeModeNotification MELEE_MODE = new MeleeModeNotification();
    private static final FireballChargeIndicator FIREBALL_CHARGE = new FireballChargeIndicator();
    private static final RaevyxBeamMeterIndicator RAEVYX_BEAM = new RaevyxBeamMeterIndicator();
    private static final IgnivorusFireBreathMeterIndicator IGNIVORUS_BREATH = new IgnivorusFireBreathMeterIndicator();
    private static final VolitansBreathMeterIndicator VOLITANS_BREATH = new VolitansBreathMeterIndicator();
    private static final DragonRideHealthBar RIDE_HEALTH = new DragonRideHealthBar();

    static {
        DragonUIRegistry.init(MELEE_MODE);
    }

    private ForgeDragonUI() {
    }

    public static void registerLayers(AddGuiOverlayLayersEvent event) {
        ForgeLayeredDraw layers = event.getLayeredDraw();
        layers.add(SaintsDragonsCommon.rl("dragon_ui"), ForgeDragonUI::render);
        layers.addConditionTo(ForgeLayeredDraw.HEALTH_BAR, () -> !shouldHideVanillaHealth());
        layers.addConditionTo(ForgeLayeredDraw.VEHICLE_HEALTH, () -> !shouldHideVanillaHealth());
    }

    private static boolean shouldHideVanillaHealth() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null
                && minecraft.player.getVehicle() instanceof DragonEntity
                && DragonUIRegistry.isUIVisible();
    }

    private static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);

        SwarmWaveBarOverlay.render(graphics, width, partialTick);
        DragonBrainDebugHud.render(graphics, width, height);
        if (ForgeClientConfig.DIVE_SPEED_LINES_ENABLED.get()) {
            SpeedLineOverlay.INSTANCE.render(graphics, width, height, partialTick);
        }

        DragonEntity currentDragon = null;
        if (minecraft.player.getVehicle() instanceof DragonEntity dragon) {
            currentDragon = dragon;
            RIDE_HEALTH.setDragon(dragon);
        }

        MELEE_MODE.render(graphics, width, height);
        if (!DragonUIRegistry.isUIVisible()) {
            return;
        }

        if (currentDragon instanceof Ignivorus ignivorus) {
            FIREBALL_CHARGE.setChargeLevel(ignivorus.getFireballChargeLevel());
            FIREBALL_CHARGE.render(graphics, width, height, partialTick);
            IGNIVORUS_BREATH.setBreathEnergy(ignivorus.getFireBreathEnergy());
            IGNIVORUS_BREATH.setBreathing(ignivorus.isBreathingFire());
            IGNIVORUS_BREATH.render(graphics, width, height, partialTick);
        } else if (currentDragon instanceof Raevyx raevyx) {
            RAEVYX_BEAM.setBeamEnergy(raevyx.getBeamEnergy());
            RAEVYX_BEAM.setBeaming(raevyx.isBeaming());
            RAEVYX_BEAM.render(graphics, width, height, partialTick);
        } else if (currentDragon instanceof Volitans volitans) {
            VOLITANS_BREATH.setWaterEnergy(volitans.getWaterBreathEnergy());
            VOLITANS_BREATH.setPoisonEnergy(volitans.getPoisonBreathEnergy());
            VOLITANS_BREATH.setBreathMode(volitans.getBreathMode());
            VOLITANS_BREATH.setBreathing(volitans.isBreathing());
            VOLITANS_BREATH.render(graphics, width, height, partialTick);
        }

        if (currentDragon != null) {
            RIDE_HEALTH.render(graphics, width, height, partialTick);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
        DragonUIKeybinds.handleKeybinds();
        MELEE_MODE.tick();
        FIREBALL_CHARGE.tick();
        RAEVYX_BEAM.tick();
        IGNIVORUS_BREATH.tick();
        VOLITANS_BREATH.tick();
    }
}
