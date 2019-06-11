package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.IEMModel;
import ru.mousecray.endmagic.util.NameAndTabUtils;
import ru.mousecray.endmagic.util.NameProvider;
import ru.mousecray.endmagic.worldgen.WorldGenTreesCloud;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class EMSapling<TreeType extends Enum<TreeType> & IStringSerializable & EMSapling.SaplingThings> extends BlockBush implements IGrowable, NameProvider, IEMModel {
    private final IProperty<TreeType> treeType;
    private final Function<Integer, TreeType> byIndex;
    private final Class<TreeType> type;

    public EMSapling(Class<TreeType> type, Function<Integer, TreeType> byIndex) {
        super();
        this.type = type;
        treeType = PropertyEnum.create("tree_type", type);
        this.byIndex = byIndex;

        //super
        blockState = new BlockStateContainer(this, treeType);
        //

        setDefaultState(blockState.getBaseState()
                .withProperty(treeType, byIndex.apply(0)));
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getValue(treeType).canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canPlaceBlockAt(worldIn, pos))
            dropBlockAsItem(worldIn, pos, state, 4);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(treeType, byIndex.apply(stack.getItemDamage())));
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

    }
    protected static AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {return SAPLING_AABB;}

    @Override
    public String name() {
        String rawName = NameAndTabUtils.getName(type);
        return rawName.substring(0, rawName.lastIndexOf('_')) + "_sapling";
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(treeType, byIndex.apply(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(treeType).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < 4; i++)
            items.add(new ItemStack(this, 1, i));
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
        for (int i = 1; i < 4; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
                    new ModelResourceLocation(getRegistryName(), "inventory,meta=" + i));
    }

    public interface SaplingThings {
        default boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
        }
    }
}
