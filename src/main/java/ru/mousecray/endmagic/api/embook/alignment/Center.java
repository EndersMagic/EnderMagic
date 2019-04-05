package ru.mousecray.endmagic.api.embook.alignment;

public class Center extends OffsetAlignment {

    public Center(float offset) {
        super(offset);
    }

    @Override
    public float resolve1(int dimension) {
        return (float)dimension / 2 + dimension * offset;
    }
}
