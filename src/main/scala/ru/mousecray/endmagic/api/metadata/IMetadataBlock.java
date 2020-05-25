package ru.mousecray.endmagic.api.metadata;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public interface IMetadataBlock {
    MetadataContainer getMetadataContainer();
    Block getBlock();
    MetadataContainer.ExtendedStateImpl correctBlockState(IBlockState state);
    int getMetaFromState(IBlockState state);
    IBlockState getStateFromMeta(int meta);
    boolean canPlaceBlockAt(IBlockState state, World world, BlockPos pos);
    default BlockStateContainer getBlockState() {return getMetadataContainer();}
    /*--------Methods which extend IBlockState format and use all of features from IFeaturesList------*/
    @Nullable default TileEntity createNewTileEntity(World world, int meta) { return getBlock().createTileEntity(world, getStateFromMeta(meta)); }
    default MetadataContainer.MetaItemBlock getCustomItemBlock() { return getMetadataContainer().createMetaItem(getBlock()); }
    default void updateTick(World world, BlockPos pos, IBlockState state, Random rand) { correctBlockState(state).updateTick(world, pos, rand); }
    default TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) { return correctBlockState(state).createTileEntity(world); }
    default boolean hasTileEntity(IBlockState state) { return correctBlockState(state).hasTileEntity(); }
    default int damageDropped(IBlockState state) { return correctBlockState(state).getDamage(); }
    default void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) { getMetadataContainer().getSubBlocks(tab, items, getBlock()); }
    default int quantityDropped(IBlockState state, int fortune, Random random) { return correctBlockState(state).quantityDropped(fortune, random); }
    default SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return correctBlockState(state).getSoundType(world, pos, entity);
    }
    /*--------Methods which extend IBlockState format and use all of features from IFeaturesList-------*/
}