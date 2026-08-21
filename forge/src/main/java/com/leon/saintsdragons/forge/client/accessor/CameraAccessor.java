package com.leon.saintsdragons.forge.client.accessor;

public interface CameraAccessor {
    void saintsdragons$invokeMove(float distance, float yaw, float pitch);
    float saintsdragons$invokeGetMaxZoom(float distance);
    void saintsdragons$invokeSetPosition(double x, double y, double z);
}
