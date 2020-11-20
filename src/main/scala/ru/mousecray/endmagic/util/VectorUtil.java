package ru.mousecray.endmagic.util;

import net.minecraft.client.renderer.Vector3d;
import net.minecraft.util.math.MathHelper;

public class VectorUtil
{
    public static float[] rotateX(float[] vec, float ang) {
        float cos = MathHelper.cos(ang);
        float sin = MathHelper.sin(ang);
        float y = vec[1] * cos - vec[2] * sin;
        float z = vec[2] * cos + vec[1] * sin;
        vec[1] = y;
        vec[2] = z;
        return vec;
    }

    public static float[] rotateY(float[] vec, float ang) {
        float cos = MathHelper.cos(ang);
        float sin = MathHelper.sin(ang);
        float x = vec[0] * cos + vec[2] * sin;
        float z = vec[2] * cos - vec[0] * sin;
        vec[0] = x;
        vec[2] = z;
        return vec;
    }

    public static float[] rotateZ(float[] vec, float ang) {
        float cos = MathHelper.cos(ang);
        float sin = MathHelper.sin(ang);
        float x = vec[0] * cos - vec[1] * sin;
        float y = vec[1] * cos + vec[0] * sin;
        vec[0] = x;
        vec[1] = y;
        return vec;
    }

    public static Vector3d rotateX(Vector3d vec, float ang) {
        float cos = MathHelper.cos(ang);
        float sin = MathHelper.sin(ang);
        double y = vec.y * cos - vec.z * sin;
        double z = vec.z * cos + vec.y * sin;
        vec.y = y;
        vec.z = z;
        return vec;
    }

    public static Vector3d rotateY(Vector3d vec, float ang) {
        float cos = MathHelper.cos(ang);
        float sin = MathHelper.sin(ang);
        double x = vec.x * cos + vec.z * sin;
        double z = vec.z * cos - vec.x * sin;
        vec.x = x;
        vec.z = z;
        return vec;
    }

    public static Vector3d rotateZ(Vector3d vec, float ang) {
        float cos = MathHelper.cos(ang);
        float sin = MathHelper.sin(ang);
        double x = vec.x * cos - vec.y* sin;
        double y = vec.y * cos + vec.x * sin;
        vec.x = x;
        vec.y = y;
        return vec;
    }
}
