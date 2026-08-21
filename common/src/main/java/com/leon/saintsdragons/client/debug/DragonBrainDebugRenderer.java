package com.leon.saintsdragons.client.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.phys.Vec3;

/**
 * Placeholder until the debug overlay is moved to Minecraft's 1.21.11 render-state pipeline.
 */
@Environment(EnvType.CLIENT)
public final class DragonBrainDebugRenderer {
    private DragonBrainDebugRenderer() {
    }

    public static void render(PoseStack poseStack, Vec3 cameraPosition) {
    }
}
