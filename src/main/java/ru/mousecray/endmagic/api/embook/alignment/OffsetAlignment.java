package ru.mousecray.endmagic.api.embook.alignment;

abstract class OffsetAlignment implements Alignment {
    public final float offset;

    public OffsetAlignment(float offset) {
        this.offset = offset;
    }
}
