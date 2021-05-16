package ru.mousecray.endmagic.blocks.trees.dragon;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public enum TreeState implements IStringSerializable {
    fine, decay;

    public static final PropertyEnum<TreeState> TREE_STATE = PropertyEnum.create("tree_state", TreeState.class);

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
