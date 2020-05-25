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

    @Nonnull static IFeaturesList EMPTY(String stateName) {
        return new IFeaturesList() {
            @Override
            public String name() {
                return stateName;
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

    default void neighbourChanged(World world, BlockPos pos, Block block, BlockPos fromPos) { throw new UnsupportedOperationException(); }
    default void updateTick(World world, BlockPos pos, IBlockState state, Random rand) { throw new UnsupportedOperationException(); }
    @Nullable default TileEntity createTileEntity(World world) { throw new UnsupportedOperationException(); }
    default boolean hasTileEntity() { throw new UnsupportedOperationException(); }
    default boolean hasTickRandomly() { throw new UnsupportedOperationException(); }
    default Material getMaterial() { throw new UnsupportedOperationException(); }
    default MapColor getMapColor(IBlockAccess world, BlockPos pos) { throw new UnsupportedOperationException(); }
    default float getHardness(World world, BlockPos pos) { throw new UnsupportedOperationException(); }
    default boolean isOpaqueCube() { throw new UnsupportedOperationException(); }
    default boolean isFullCube() { throw new UnsupportedOperationException(); }
    default int getLightValue(IBlockAccess world, BlockPos pos) { throw new UnsupportedOperationException(); }
    default int getLightOpacity(IBlockAccess world, BlockPos pos) { throw new UnsupportedOperationException(); }
    @Nullable default EnumBlockRenderType getRenderType() { throw new UnsupportedOperationException(); }
    default boolean canProvidePower() { throw new UnsupportedOperationException(); }
    default boolean canEntitySpawn(Entity entity) { throw new UnsupportedOperationException(); }
    default SoundType getSoundType(World world, BlockPos pos, Entity entity) { throw new UnsupportedOperationException(); }
    default boolean isTopSolid() { throw new UnsupportedOperationException(); }
    default int quantityDropped(int fortune, Random rand) { throw new UnsupportedOperationException(); }
    default BlockFaceShape getFaceShape(IBlockAccess world, BlockPos pos, EnumFacing facing) { throw new UnsupportedOperationException(); }
    @Nullable default AxisAlignedBB getCollisionAABB(IBlockAccess world, BlockPos pos) { throw new UnsupportedOperationException(); }
    @Nullable default AxisAlignedBB getSelectedAABB(IBlockAccess world, BlockPos pos) { throw new UnsupportedOperationException(); }
    default AxisAlignedBB getBoundingBox(IBlockAccess world, BlockPos pos) { throw new UnsupportedOperationException(); }
    default int getWeakPower(IBlockAccess world, BlockPos pos, EnumFacing side) { throw new UnsupportedOperationException(); }
    default int getStrongPower(IBlockAccess world, BlockPos pos, EnumFacing side) { throw new UnsupportedOperationException(); }
    default int getDamage() { return ordinal(); }

    /**
     * Works only with MetaItemBlock from MetadataContainer
     *
     * @param world in the player present
     * @param pos   of this block
     * @return true if block may be place
     */
    default boolean canPlaceBlockAt(World world, BlockPos pos) { throw new UnsupportedOperationException();}

    @Override default String getName() { return name().toLowerCase(); }
    String name();
    int ordinal();
    default byte getPriority() { return 0; }
}