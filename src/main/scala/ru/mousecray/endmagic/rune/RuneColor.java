package ru.mousecray.endmagic.rune;

public enum RuneColor {
    white(255, 255, 255),
    green(0, 255, 0)
    ;

    public final byte r;
    public final byte g;
    public final byte b;

    RuneColor(int red, int green, int blue) {
        this.r = (byte) red;
        this.g = (byte) green;
        this.b = (byte) blue;
    }
}
