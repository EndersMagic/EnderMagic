package ru.mousecray.endmagic.util;

import net.minecraft.client.renderer.Vector3d;
import net.minecraft.util.math.MathHelper;

public class VectorUtil
{
    public static Vector3d rotate(Vector3d vec, Vector3d angs) {
        VectorUtil.rotateX(vec, angs.x);
        VectorUtil.rotateY(vec, angs.y);
        VectorUtil.rotateZ(vec, angs.z);
        return vec;
    }

    public static Vector3d rotate(Vector3d vec, double x, double y, double z) {
        VectorUtil.rotateX(vec, x);
        VectorUtil.rotateY(vec, y);
        VectorUtil.rotateZ(vec, z);
        return vec;
    }

    public static Vector3d rotateX(Vector3d vec, double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double y = vec.y * cos - vec.z * sin;
        double z = vec.z * cos + vec.y * sin;
        vec.y = y;
        vec.z = z;
        return vec;
    }

    public static Vector3d rotateY(Vector3d vec, double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double x = vec.x * cos + vec.z * sin;
        double z = vec.z * cos - vec.x * sin;
        vec.x = x;
        vec.z = z;
        return vec;
    }

    public static Vector3d rotateZ(Vector3d vec, double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double x = vec.x * cos - vec.y* sin;
        double y = vec.y * cos + vec.x * sin;
        vec.x = x;
        vec.y = y;
        return vec;
    }

    public static Vector3d of(double x, double y, double z)
    {
        Vector3d vec = new Vector3d();
        vec.x = x + 0;
        vec.y = y + 0;
        vec.z = z + 0;
        return vec;
    }
}
