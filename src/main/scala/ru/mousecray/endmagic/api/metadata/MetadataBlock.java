package ru.mousecray.endmagic.api.metadata;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class MetadataBlock extends Block implements IMetadataBlock {

    private final MetadataContainer overrideBlockState;

    public MetadataBlock(Material material) {
        super(material);
        overrideBlockState = (MetadataContainer) blockState;
        //Use state tick randomly if any state has tick randomly
        setTickRandomly(overrideBlockState.hasBlockTickRandomly());
    }

    @Override
    public MetadataContainer getMetadataContainer() {
        return overrideBlockState;
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    public abstract BlockStateContainer createBlockState();

    @Override
    public MetadataContainer.ExtendedStateImpl correctBlockState(IBlockState state) {
        return (MetadataContainer.ExtendedStateImpl) state;
    }

    @Override
    public MetadataContainer.ExtendedStateImpl getStateFromMeta(int meta) {
        return overrideBlockState.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return overrideBlockState.getMetaFromState(state);
    }

    /*----------State sensitive version of canPlaceBlockAt----------*/
    @Override
    @Deprecated //EM: State sensitive version
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos);
    }

    @Override
    public boolean canPlaceBlockAt(IBlockState state, World world, BlockPos pos) {
        return canPlaceBlockAt(world, pos);
    }
    /*----------State sensitive version of canPlaceBlockAt----------*/

    @Override
    public MetadataContainer.MetaItemBlock getCustomItemBlock() {
        return overrideBlockState.createMetaItem(this);
    }
}