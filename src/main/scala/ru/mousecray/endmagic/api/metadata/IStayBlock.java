package ru.mousecray.endmagic.api.metadata;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface IStayBlock {
    boolean canPlaceBlockAt(IBlockState state, World world, BlockPos pos);
}