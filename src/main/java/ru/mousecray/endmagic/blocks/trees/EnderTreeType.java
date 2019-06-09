package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum EnderTreeType implements IStringSerializable {
    DRAGON("dragon", MapColor.PURPLE),
    NATURAL("natural", MapColor.BLUE),
    IMMORTAL("immortal", MapColor.EMERALD),
    PHANTOM("phantom", MapColor.SILVER);

    private final String name;
    private final MapColor mapColor;

    EnderTreeType(String name, MapColor mapColor) {
        this.name = name;
        this.mapColor = mapColor;
    }

    @Override
    public String getName() {
        return name;
    }
}
