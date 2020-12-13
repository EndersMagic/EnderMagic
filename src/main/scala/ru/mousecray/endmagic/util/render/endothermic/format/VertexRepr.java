package ru.mousecray.endmagic.util.render.endothermic.format;

public enum VertexRepr {
    _1, _2, _3, _4;

    @Override
    public String toString() {
        return "Vertex(" + index() + ")";
    }

    public int index() {
        return ordinal();
    }

}