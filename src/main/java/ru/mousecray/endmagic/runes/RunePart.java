package ru.mousecray.endmagic.runes;

public class RunePart {
    public final int x, y;
    public final EnumPartType type;

    public RunePart(int x, int y, EnumPartType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public enum EnumPartType {
        FullPixel
    }
}
