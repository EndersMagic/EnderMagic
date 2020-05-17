package ru.mousecray.endmagic.capability.chunk;

import ru.mousecray.endmagic.rune.RuneColor;

public class RunePart {

    public final byte color;

    public RunePart(byte color) {
        this.color = color;
    }

    public RunePart(RuneColor color) {
        this.color = (byte) color.ordinal();
    }

    public RuneColor color() {
        return RuneColor.values()[color];
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RunePart) && ((RunePart) obj).color == color;
    }

    @Override
    public String toString() {
        return "RunePart(" + color + ")";
    }
}
