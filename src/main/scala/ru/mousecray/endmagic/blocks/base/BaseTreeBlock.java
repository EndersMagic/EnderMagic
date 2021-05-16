package ru.mousecray.endmagic.blocks.base;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.List;

public abstract class BaseTreeBlock extends AutoMetaBlock {

    public final EnderBlockTypes.EnderTreeType treeType;

    public BaseTreeBlock(Material blockMaterialIn, EnderBlockTypes.EnderTreeType treeType) {
        super(blockMaterialIn, treeType.getMapColor());
        this.treeType = treeType;
    }

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of();
    }

    @Override
    public String getCustomName() {
        return "ender_tree_" + typeName() + "_" + suffix();
    }

    public String typeName() {
        return treeType.getName();
    }

    protected abstract String suffix();
}
