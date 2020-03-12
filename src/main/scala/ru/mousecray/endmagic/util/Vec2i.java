package ru.mousecray.endmagic.util;

public class Vec2i {
    public final int x,y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vec2i(x = "+x+", y = "+y+")";
    }
}
