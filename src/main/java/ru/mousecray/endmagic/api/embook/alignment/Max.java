package ru.mousecray.endmagic.api.embook.alignment;

public class Max extends OffsetAlignment {

    public Max(float offset) {
        super(offset);
    }

    @Override
    public float resolve1(int dimension) {
        return dimension + dimension * offset;
    }
}
