package ru.mousecray.endmagic.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
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

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EMLeaves<TreeType extends Enum<TreeType> & IStringSerializable> extends BlockLeaves implements NameProvider, IEMModel {
    private final IProperty<TreeType> treeType;
    private final Function<Integer, TreeType> byIndex;
    private final Class<TreeType> type;

    public EMLeaves(Class<TreeType> type, Function<Integer, TreeType> byIndex) {
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

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(treeType, byIndex.apply(stack.getItemDamage())));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < 4; i++)
            items.add(new ItemStack(this, 1, i));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int type = meta & 3;
        return getDefaultState()
                .withProperty(treeType, byIndex.apply(type));
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
    public String name() {
        String rawName = NameAndTabUtils.getName(type);
        return rawName.substring(0, rawName.lastIndexOf('_')) + "_leaves";
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
        for (int i = 1; i < 4; i++)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
                    new ModelResourceLocation(getRegistryName(), "inventory,meta=" + i));
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return ImmutableList.of(new ItemStack(
                Item.getItemFromBlock(this), 1,
                world.getBlockState(pos).getValue(treeType).ordinal()));
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            if (worldIn.isAreaLoaded(pos, 2)) {
                if (findingArea(pos)
                        .noneMatch(pos1 -> worldIn.getBlockState(pos1).getBlock()
                                .canSustainLeaves(state, worldIn, pos))) {
                    dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
                    worldIn.setBlockToAir(pos);
                }

            }
        }
    }

    private Stream<BlockPos> findingArea(BlockPos pos) {
        return IntStream.range(-5,5)
                .mapToObj(x->
                        IntStream.range(-5,5)
                                .mapToObj(y->
                                        IntStream.range(-5,5)
                                                .mapToObj(z->
                                                        pos.add(x,y,z))).flatMap(Function.identity())).flatMap(Function.identity());
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
    }
}
