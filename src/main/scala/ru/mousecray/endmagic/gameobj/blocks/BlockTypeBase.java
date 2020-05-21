package ru.mousecray.endmagic.gameobj.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public interface BlockTypeBase {
    default TileEntity createTileEntity(World world, IBlockState state) {
        return null;
    }

    default boolean hasTileEntity(IBlockState state) {
        return false;
    }

    default EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    default boolean isFullCube() {
        return true;
    }

    default boolean isOpaqueCube() {
        return true;
    }

    default boolean hasTickRandomly() {
        return false;
    }

    @Nullable
    default MapColor getMapColor() {
        return null;
    }

    default void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

    }
}