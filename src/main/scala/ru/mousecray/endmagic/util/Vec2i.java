package ru.mousecray.endmagic.util;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Vec2i {
    public final int x, y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vec2i(x = " + x + ", y = " + y + ")";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(x).append(y).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Vec2i) && ((Vec2i) obj).x == x && ((Vec2i) obj).y == y;
    }
}
