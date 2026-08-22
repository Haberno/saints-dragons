package com.leon.saintsdragons.client.renderer.layer.ignivorus;

import com.leon.saintsdragons.client.renderer.GeoRenderDataTickets;
import com.leon.saintsdragons.client.renderer.state.SaintsDragonsLivingEntityRenderState;
import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class IgnivorusMouthSmokeLayer
        extends GeoRenderLayer<Ignivorus, Void, SaintsDragonsLivingEntityRenderState> {
    public IgnivorusMouthSmokeLayer(
            GeoRenderer<Ignivorus, Void, SaintsDragonsLivingEntityRenderState> renderer) {
        super(renderer);
    }

    private int lastSmokeTick = Integer.MIN_VALUE;

    @Override
    public void submitRenderTask(RenderPassInfo<SaintsDragonsLivingEntityRenderState> renderPassInfo,
                                 SubmitNodeCollector renderTasks) {
        Ignivorus animatable =
                (Ignivorus) renderPassInfo.getGeckolibData(GeoRenderDataTickets.ANIMATABLE);
        if (animatable == null || !animatable.isBreathingFire()
                || !(animatable.level() instanceof ClientLevel clientLevel)) {
            return;
        }

        float partialTick = renderPassInfo.renderState().getPartialTick();
        Vec3 start = animatable.getFireBreathStartAnchor(partialTick);
        if (start == null) {
            return;
        }

        Vec3 look = Vec3.directionFromRotation(animatable.getXRot(), animatable.yHeadRot).normalize();
        Vec3 spawnCenter = start.add(look.scale(0.35D));
        // submitRenderTask runs once per render pass; gate on the tick so the emission
        // rate does not scale with framerate (and does not double up per render layer).
        if (animatable.tickCount == lastSmokeTick) {
            return;
        }
        lastSmokeTick = animatable.tickCount;

        RandomSource random = animatable.getRandom();
        for (int i = 0; i < 4; i++) {
            double px = spawnCenter.x + (random.nextDouble() - 0.5D) * 0.2D;
            double py = spawnCenter.y + random.nextDouble() * 0.15D;
            double pz = spawnCenter.z + (random.nextDouble() - 0.5D) * 0.2D;
            clientLevel.addParticle(ParticleTypes.SMOKE, px, py, pz, 0.0D, 0.01D, 0.0D);
            if (random.nextFloat() < 0.35F) {
                clientLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, px, py, pz, 0.0D, 0.02D, 0.0D);
            }
        }
    }
}
