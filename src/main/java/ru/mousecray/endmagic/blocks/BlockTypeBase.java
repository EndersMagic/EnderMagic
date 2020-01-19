package ru.mousecray.endmagic.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface BlockTypeBase {
    default TileEntity createTileEntity(World world, IBlockState state) {
        return null;
    }

    default boolean hasTileEntity(IBlockState state) {
        return false;
    }
}
