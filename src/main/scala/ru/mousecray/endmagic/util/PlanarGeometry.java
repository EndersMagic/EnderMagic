package ru.mousecray.endmagic.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

public class PlanarGeometry {

    public static Vec2i projectTo2d(Vec3i vertex, EnumFacing facing) {
        int x = vertex.getX();
        int z = vertex.getZ();
        int y = vertex.getY();
        switch (facing) {
            case UP:
                return new Vec2i(z, x);
            case DOWN:
                return new Vec2i(15 - z,x);
            case EAST:
                return new Vec2i( 15 - y,15 - z);
            case WEST:
                return new Vec2i(15 - y,z);
            case NORTH:
                return new Vec2i(15 - y,15 - x);
            case SOUTH:
                return new Vec2i(15 - y,x);
            default:
                throw new UnsupportedOperationException("no more facings");
        }
    }
}
