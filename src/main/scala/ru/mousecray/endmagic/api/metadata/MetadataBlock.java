package ru.mousecray.endmagic.api.metadata;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

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

    /*@formatter:off----Magic methods which you must implement in IMetadataBlock child classes----*/
    @Override protected abstract BlockStateContainer createBlockState();
    @Override public MetadataContainer getMetadataContainer() { return overrideBlockState; }
    @Override public MetadataContainer.ExtendedStateImpl getStateFromMeta(int meta) { return overrideBlockState.getStateFromMeta(meta); }
    @Override public int getMetaFromState(IBlockState state) { return overrideBlockState.getMetaFromState(state); }
    @Override public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) { correctBlockState(state).updateTick(world, pos, rand); }
    @Override public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) { return correctBlockState(state).createTileEntity(world); }
    @Override public boolean hasTileEntity(IBlockState state) { return correctBlockState(state).hasTileEntity(); }
    @Override public int damageDropped(IBlockState state) { return correctBlockState(state).getDamage(); }
    @Override public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) { overrideBlockState.getSubBlocks(tab, items, this); }
    @Override public int quantityDropped(IBlockState state, int fortune, Random random) { return correctBlockState(state).quantityDropped(fortune, random); }
    @Override public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return correctBlockState(state).getSoundType(world, pos, entity);
    }
    /*@formatter:on----Magic methods which you must implement in IMetadataBlock child classes----*/

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
}