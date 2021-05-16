package ru.mousecray.endmagic.blocks.trees;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.blocks.base.BaseTreeBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.worldgen.trees.WorldGenEnderTree;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.BlockSapling.SAPLING_AABB;

public class EMSapling extends BaseTreeBlock implements IGrowable {

    public EMSapling(EnderBlockTypes.EnderTreeType treeType) {
        super(Material.PLANTS, treeType);
        setResistance(0.0F);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
    }

    @Override
    protected String suffix() {
        return "sapling";
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return true;
    }

    public boolean checkPlacement(World worldIn, BlockPos pos) {
        return EMUtils.isSoil(worldIn.getBlockState(pos.down()), EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!checkPlacement(world, pos)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            dropBlockAsItem(world, pos, state, 4);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!checkPlacement(world, pos))
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        else
            world.setBlockState(pos, state);

    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        WorldGenEnderTree generator = treeType.generator();
        if (generator != null)
            generator.generate(world, rand, pos);
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
}