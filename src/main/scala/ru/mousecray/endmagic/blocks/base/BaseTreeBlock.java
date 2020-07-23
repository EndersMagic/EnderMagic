package ru.mousecray.endmagic.blocks.base;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public abstract class BaseTreeBlock extends AutoMetaSubtypedBlock {

    @Override
    public List<IProperty> properties() {
        return ImmutableList.of(TREE_TYPE);
    }

    public BaseTreeBlock(Material materialIn) {
        super(materialIn);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(TREE_TYPE).getMapColor();
    }

    @Override
    public String getCustomName() {
        return "ender_tree_" + suffix();
    }

    protected abstract String suffix();
}
