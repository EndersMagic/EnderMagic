package ru.mousecray.endmagic.gameobj.blocks.trees;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.api.metadata.MetadataBlock;
import ru.mousecray.endmagic.api.metadata.PropertyFeature;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class EnderPlanks extends MetadataBlock {

    public static final PropertyFeature<EnderBlockTypes.EnderTreeType> TREE_TYPE = EnderBlockTypes.TREE_TYPE;

    public EnderPlanks() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(7.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    protected BlockStateContainer createBlockStateContainer() {
        return BlockStateGenerator.create(this).addFeature(TREE_TYPE).buildContainer();
    }
}