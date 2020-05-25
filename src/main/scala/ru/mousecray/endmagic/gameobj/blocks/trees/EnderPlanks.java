package ru.mousecray.endmagic.gameobj.blocks.trees;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.gameobj.blocks.utils.EMMetadataBlock;

import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public class EnderPlanks extends EMMetadataBlock {

    public EnderPlanks() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(7.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return BlockStateGenerator.create(this).addFeature(TREE_TYPE, true).buildContainer();
    }
}