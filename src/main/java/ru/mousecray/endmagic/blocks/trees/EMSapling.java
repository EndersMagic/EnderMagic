package ru.mousecray.endmagic.blocks.trees;

import static net.minecraft.block.BlockSapling.SAPLING_AABB;

import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

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

public class EMSapling<TreeType extends Enum<TreeType> & IStringSerializable & EMSapling.SaplingThings & BlockTypeBase> extends VariativeBlock<TreeType> implements IGrowable {

    public EMSapling(Class<TreeType> type, Function<TreeType, MapColor> mapFunc) {
        super(type, Material.PLANTS, "sapling", mapFunc);

        setResistance(0.0F);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.getBlockState(pos).getValue(blockType).canPlaceBlockAt(worldIn, pos)) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            dropBlockAsItem(worldIn, pos, state, 4);
        }
    }


    @Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TreeType blockType1 = byIndex.apply(stack.getItemDamage());
        worldIn.setBlockState(pos, state.withProperty(blockType, blockType1));
        if (!blockType1.canPlaceBlockAt(worldIn, pos))
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        state.getValue(blockType).grow(worldIn, rand, pos, state);
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
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

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
            return EMUtils.isSoil(world.getBlockState(pos.down()), true, false, EndSoilType.DIRT, EndSoilType.GRASS);
        }

        default void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

        }
    }
}