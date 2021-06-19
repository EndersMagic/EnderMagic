package ru.mousecray.endmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.ByBlockNotifiable;

import java.util.function.Supplier;

public class BlockWithNotifiableTile<A extends TileEntity & ByBlockNotifiable> extends BlockWithTile<A> {
    public BlockWithNotifiableTile(Material materialIn, Supplier<A> factory) {
        super(materialIn, factory);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote)
            tile(worldIn, pos).neighborChanged();
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote)
            tile(worldIn, pos).breakBlock();
        super.breakBlock(worldIn, pos, state);
    }
}
