package ru.mousecray.endmagic.util;

import net.minecraft.client.renderer.Vector3d;

public class VectorUtil
{

    public static void rotateFromDirs(Vector3d vec, Vector3d angs)
    {
        if(angs.x == 0)
            angs.x = 0.001;
        if(angs.y == 0)
            angs.y = 0.001;
        if(angs.z == 0)
            angs.z = 0.001;

        VectorUtil.rotateX(vec, findAngleIfCisHypotenuse(angs.x, angs.z));
        VectorUtil.rotateY(vec, findAngleIfCisHypotenuse(angs.x, angs.y));
    }

    //returns angle between a and b
    private static double findAngle(double a, double b, double c)
    {
        return Math.acos((a * a + b * b - c * c) / (2 * a * b));
    }


    //returns angle between a and b if c is hypotenuse
    private static double findAngleIfCisHypotenuse(double a, double b)
    {
        double c = Math.sqrt(Math.abs(a * a + b * b));

        return findAngle(a, b, c);
    }

    //returns angle between a and b if c is hypotenuse
    private static double findAngleIfCisNotHypotenuse(double a, double b)
    {
        double c = Math.sqrt(Math.abs(a * a - b * b));

        return findAngle(a, b, c);
    }

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
        vec.x = x + 0.000001;
        vec.y = y + 0.000001;
        vec.z = z + 0.000001;
        return vec;
    }
}
