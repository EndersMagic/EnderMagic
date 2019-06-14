package ru.mousecray.endmagic.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.blocks.VariativeBlock;
import ru.mousecray.endmagic.init.EMBlocks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EMLeaves<TreeType extends Enum<TreeType> & IStringSerializable> extends VariativeBlock<TreeType> implements IShearable {

    public EMLeaves(Class<TreeType> type, Function<Integer, TreeType> byIndex) {
        super(type, byIndex, Material.LEAVES, "_leaves");

        setTickRandomly(true);
        setHardness(0.2F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);

        setDefaultState(blockState.getBaseState()
                .withProperty(blockType, byIndex.apply(0)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(EMBlocks.enderSapling);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(blockType).ordinal();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Blocks.LEAVES.getDrops(drops, world, pos, state, fortune);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return item.getItem().getRegistryName().getResourcePath().contains("shears");
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return Blocks.LEAVES.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return ImmutableList.of(new ItemStack(
                Item.getItemFromBlock(this), 1,
                damageDropped(world.getBlockState(pos))));
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
        return IntStream.range(-5, 5)
                .mapToObj(x ->
                        IntStream.range(-5, 5)
                                .mapToObj(y ->
                                        IntStream.range(-5, 5)
                                                .mapToObj(z ->
                                                        pos.add(x, y, z))).flatMap(Function.identity())).flatMap(Function.identity());
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
    }


    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Blocks.LEAVES.randomDisplayTick(stateIn, worldIn, pos, rand);
    }
}
