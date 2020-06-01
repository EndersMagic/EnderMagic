package ru.mousecray.endmagic.api.metadata;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMetadataBlock {
    MetadataContainer getMetadataContainer();
    boolean canPlaceBlockAt(IBlockState state, World world, BlockPos pos);
    default MetadataContainer.ExtendedStateImpl correctBlockState(IBlockState state) {
        return (MetadataContainer.ExtendedStateImpl) state;
    }
    default MetadataContainer.MetaItemBlock getItemBlock(Block block) { return getMetadataContainer().createMetaItem(block); }
}