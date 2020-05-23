package ru.mousecray.endmagic.api.metadata;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.registry.ITechnicalBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class MetadataBlock extends Block implements ITechnicalBlock {

    private final MetadataContainer overrideBlockState;

    public MetadataBlock(Material material) {
        super(material);
        overrideBlockState = (MetadataContainer) blockState;
        setTickRandomly(overrideBlockState.hasBlockTickRandomly());
    }

    @Override
    public MetadataContainer getBlockState() {
        return overrideBlockState;
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return createBlockStateContainer();
    }

    protected abstract BlockStateContainer createBlockStateContainer();

    protected MetadataContainer.ExtendedStateImpl correctBlockState(IBlockState state) {
        return (MetadataContainer.ExtendedStateImpl) state;
    }

    @Override
    public MetadataContainer.ExtendedStateImpl getStateFromMeta(int meta) {
        return overrideBlockState.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return overrideBlockState.getMetaFromState(state);
    }

    /*@formatter:off--------Methods which extend IBlockState format and use all of features from IFeaturesList------*/
    @Override public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) { return correctBlockState(state).getSoundType(); }
    @Override public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) { correctBlockState(state).updateTick(world, pos, rand); }
    @Override public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) { return correctBlockState(state).createTileEntity(); }
    @Override public boolean hasTileEntity(IBlockState state) { return correctBlockState(state).hasTileEntity(); }
    @Override public int damageDropped(IBlockState state) { return correctBlockState(state).getDamage(); }
    @Override public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) { overrideBlockState.getSubBlocks(tab, items, this); }
    @Override public int quantityDropped(IBlockState state, int fortune, Random random) { return correctBlockState(state).quantityDropped(fortune, random); }
    /*@formatter:on-------------------------------------------------------------------------------------------------*/

    /*----------Methods inherited by ITechnicalBlock----------*/
    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        overrideBlockState.registerItemModels(this);
    }

    @Override
    public ItemBlock getCustomItemBlock() {
        return overrideBlockState.createMetaItem(this);
    }

    @Nullable @Override
    public CreativeTabs getCustomCreativeTab() {
        return EM.EM_CREATIVE;
    }
    /*--------------------------------------------------------*/
}