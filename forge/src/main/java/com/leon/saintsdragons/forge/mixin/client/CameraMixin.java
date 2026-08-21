package com.leon.saintsdragons.forge.mixin.client;

import com.leon.saintsdragons.forge.client.accessor.CameraAccessor;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraMixin extends CameraAccessor {
    @Invoker("move")
    void saintsdragons$invokeMove(float distance, float yaw, float pitch);

    @Invoker("getMaxZoom")
    float saintsdragons$invokeGetMaxZoom(float distance);

    @Invoker("setPosition")
    void saintsdragons$invokeSetPosition(double x, double y, double z);
}
