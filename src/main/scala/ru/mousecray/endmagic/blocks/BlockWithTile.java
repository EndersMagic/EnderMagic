package ru.mousecray.endmagic.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class BlockWithTile<A extends TileEntity> extends BlockContainer {
    private final Supplier<A> factory;

    public BlockWithTile(Material materialIn, Supplier<A> factory) {
        super(materialIn);
        this.factory = factory;
    }

    public A tile(World world, BlockPos pos) {
        return (A) world.getTileEntity(pos);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return factory.get();
    }
}