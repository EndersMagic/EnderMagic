package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import ru.mousecray.endmagic.blocks.base.BaseTreeBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class EMPlanks extends BaseTreeBlock {

    public EMPlanks(EnderBlockTypes.EnderTreeType treeType) {
        super(Material.WOOD, treeType);
        setHardness(2.5F);
        setResistance(7.0F);
        setSoundType(SoundType.WOOD);

        for (IBlockState validState : getBlockState().getValidStates())
            setHarvestLevel("axe", 0, validState);
    }

    @Override
    protected String suffix() {
        return "planks";
    }
}
