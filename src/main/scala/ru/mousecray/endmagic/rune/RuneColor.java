package ru.mousecray.endmagic.rune;

public enum RuneColor {
    white(255, 255, 255),
    green(0, 255, 0)
    ;

    public final int r;
    public final int g;
    public final int b;

    RuneColor(int red, int green, int blue) {
        this.r =  red;
        this.g =  green;
        this.b = blue;
    }

    @Override
    public String toString() {
        return name()+"("+r+", "+g+", "+b+")";
    }
}
