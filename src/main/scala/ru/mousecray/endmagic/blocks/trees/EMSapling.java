package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.blocks.BlockTypeBase;
import ru.mousecray.endmagic.blocks.VariativeBlock;
import ru.mousecray.endmagic.world.gen.trees.WorldGenEnderTree;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

import static net.minecraft.block.BlockSapling.SAPLING_AABB;

public class EMSapling<TreeType extends Enum<TreeType> & IStringSerializable & EMSapling.SaplingThings & BlockTypeBase> extends VariativeBlock<TreeType> implements IGrowable {

    private Function<TreeType, WorldGenEnderTree> genFunc;

    public EMSapling(Class<TreeType> type, Function<TreeType, MapColor> mapFunc, Function<TreeType, WorldGenEnderTree> genFunc) {
        super(type, Material.PLANTS, "sapling", mapFunc);
        this.genFunc = genFunc;
        setResistance(0.0F);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.getBlockState(pos).getValue(blockType).canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            dropBlockAsItem(world, pos, state, 4);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        TreeType blockType1 = byIndex.apply(stack.getItemDamage());
        world.setBlockState(pos, state.withProperty(blockType, blockType1));
        if (!blockType1.canPlaceBlockAt(world, pos)) world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        state.getValue(blockType).grow(world, rand, pos, state, genFunc.apply(state.getValue(blockType)));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT; }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    public interface SaplingThings {
        default boolean canPlaceBlockAt(World world, BlockPos pos) {
            //TODO: add custom end grass and remove STONE from this
            return EMUtils.isSoil(world.getBlockState(pos.down()), EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS);
        }

        default void grow(World world, Random rand, BlockPos pos, IBlockState state, @Nullable WorldGenEnderTree generator) {
            if (generator != null) generator.generate(world, rand, pos);
        }
    }
}