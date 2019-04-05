package ru.mousecray.endmagic.api.embook.alignment;

public class Min extends OffsetAlignment {
    public Min(float offset) {
        super(offset);
    }

    @Override
    public float resolve1(int dimension) {
        return dimension * offset;
    }
}
