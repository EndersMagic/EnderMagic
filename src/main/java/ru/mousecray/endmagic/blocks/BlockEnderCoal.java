package ru.mousecray.endmagic.blocks;

import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA;

public class BlockEnderCoal extends BlockNamed implements BlockColored {
    private final RGBA color;

    public BlockEnderCoal(String name, RGBA color) {
        super(name);
        this.color = color;
    }

    @Override
    public RGBA color() {
        return color;
    }
}
