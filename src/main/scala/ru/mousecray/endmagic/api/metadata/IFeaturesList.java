package ru.mousecray.endmagic.api.metadata;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public interface IFeaturesList extends IStringSerializable {

    @Nonnull static IFeaturesList EMPTY(String blockName) {
        return new IFeaturesList() {
            @Override
            public String name() {
                return blockName;
            }

            @Override
            public int ordinal() {
                return 0;
            }

            @Override
            public byte getPriority() {
                return -128;
            }
        };
    }

    default void neighbourChanged(World world, BlockPos pos, Block block, BlockPos fromPos) {}
    default void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {}
    @Nullable default TileEntity createTileEntity() { return null; }
    default boolean hasTileEntity(IBlockState state) { return false; }
    default boolean hasTickRandomly() { return false; }
    default Material getMaterial(IBlockState state) { return state.getBlock().getMaterial(state); }
    default MapColor getMapColor(IBlockState state, IBlockAccess access, BlockPos pos) { return state.getBlock().getMapColor(state, access, pos); }
    default float getHardness(IBlockState state, World world, BlockPos pos) { return state.getBlock().getBlockHardness(state, world, pos); }
    default boolean isOpaqueCube(IBlockState state) { return state.getBlock().isOpaqueCube(state); }
    default boolean isFullCube(IBlockState state) { return state.getBlock().isFullCube(state); }
    default int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) { return state.getBlock().getLightValue(state, world, pos); }
    default int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) { return state.getBlock().getLightOpacity(state, world, pos); }
    @Nullable default EnumBlockRenderType getRenderType(IBlockState state) { return state.getBlock().getRenderType(state); }
    default boolean canProvidePower(IBlockState state) { return state.getBlock().canProvidePower(state); }
    default boolean canEntitySpawn(IBlockState state, Entity entity) { return state.getBlock().canEntitySpawn(state, entity); }
    default SoundType getSoundType(IBlockState state) { return state.getBlock().getSoundType(); }
    default boolean isTopSolid(IBlockState state) { return state.getBlock().isTopSolid(state); }
    default int quantityDropped(IBlockState state, int fortune, Random rand) { return state.getBlock().quantityDropped(state, fortune, rand); }
    default BlockFaceShape getFaceShape(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return state.getBlock().getBlockFaceShape(world, state, pos, facing);
    }
    @Nullable default AxisAlignedBB getCollisionAABB(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getBlock().getCollisionBoundingBox(state, world, pos);
    }
    @Nullable default AxisAlignedBB getSelectedAABB(IBlockState state, World world, BlockPos pos) {
        return state.getBlock().getSelectedBoundingBox(state, world, pos);
    }
    default AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
        return state.getBlock().getBoundingBox(state, access, pos);
    }
    default int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return state.getBlock().getWeakPower(state, blockAccess, pos, side);
    }
    default int getStrongPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return state.getBlock().getStrongPower(state, blockAccess, pos, side);
    }
    default int getDamage() { return ordinal(); }
    @Override default String getName() { return name().toLowerCase(); }
    String name();
    int ordinal();
    default byte getPriority() { return 0; }
}