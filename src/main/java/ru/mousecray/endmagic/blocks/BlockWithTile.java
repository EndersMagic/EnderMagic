package ru.mousecray.endmagic.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWithTile<A extends TileEntity> extends BlockContainer {
    public BlockWithTile(Material materialIn) {
        super(materialIn);
    }

    public A tile(World world, BlockPos pos) {
        return (A) world.getTileEntity(pos);
    }
}
