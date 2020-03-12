package ru.mousecray.endmagic.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

public class PlanarGeometry {

    public static Vec2i projectTo2d(Vec3i vertex, EnumFacing facing) {
        switch (facing) {
            case UP:
                return new Vec2i(vertex.getX(), vertex.getZ());
            case DOWN:
                return new Vec2i(vertex.getX(), 15 - vertex.getZ());
            case EAST:
                return new Vec2i(15 - vertex.getZ(), 15 - vertex.getY());
            case WEST:
                return new Vec2i(vertex.getZ(), 15 - vertex.getY());
            case NORTH:
                return new Vec2i(15 - vertex.getX(), 15 - vertex.getY());
            case SOUTH:
                return new Vec2i(vertex.getX(), 15 - vertex.getY());
            default:
                throw new UnsupportedOperationException("no more facings");
        }
    }
}
