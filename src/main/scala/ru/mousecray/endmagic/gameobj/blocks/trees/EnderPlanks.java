package ru.mousecray.endmagic.gameobj.blocks.trees;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;

import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public class EnderPlanks extends MetadataBlock {

    public EnderPlanks() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(7.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addFeatures(TREE_TYPE).buildContainer();
    }
}