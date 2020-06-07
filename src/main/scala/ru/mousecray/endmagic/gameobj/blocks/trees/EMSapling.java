package ru.mousecray.endmagic.gameobj.blocks.trees;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.gameobj.blocks.BlockTypeBase;
import ru.mousecray.endmagic.gameobj.blocks.VariativeBlock;
import ru.mousecray.endmagic.gameobj.blocks.utils.AutoMetadataBlock;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.block.BlockSapling.SAPLING_AABB;
import static ru.mousecray.endmagic.util.EnderBlockTypes.treeType;

public class EMSapling extends AutoMetadataBlock implements IGrowable {

    public EMSapling() {
        super(Material.PLANTS);
        setResistance(0.0F);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            dropBlockAsItem(world, pos, state, 4);
        }
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
        state.getValue(treeType).grow(world, rand, pos, state);
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
    public List<IProperty<?>> properties() {
        return ImmutableList.of(treeType);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            if (!world.isAreaLoaded(pos, 1)) return;
            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) grow(world, rand, pos, state);
        }
    }

    public interface SaplingThings {
        default void grow(World world, Random rand, BlockPos pos, IBlockState state) {
            WorldGenerator generator = getGenerator();
            if (generator != null) generator.generate(world, rand, pos);
        }

        @Nullable
        default WorldGenerator getGenerator() {
            return null;
        }
    }
}