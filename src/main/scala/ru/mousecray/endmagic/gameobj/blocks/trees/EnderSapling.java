package ru.mousecray.endmagic.gameobj.blocks.trees;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.metadata.BlockStateGenerator;
import ru.mousecray.endmagic.gameobj.blocks.utils.EMMetadataBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

import static net.minecraft.block.BlockSapling.SAPLING_AABB;
import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public class EnderSapling extends EMMetadataBlock implements IGrowable {

    public EnderSapling() {
        super(Material.PLANTS);
        setResistance(0.0F);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return BlockStateGenerator.create(this).addFeature(TREE_TYPE, true).buildContainer();
    }

    @Override
    public boolean canPlaceBlockAt(IBlockState state, World world, BlockPos pos) {
        if (state.getValue(TREE_TYPE) == EnderBlockTypes.EnderTreeType.DRAGON) return Arrays.stream(EnumFacing.HORIZONTALS)
                .map(pos::offset)
                .map(world::getBlockState)
                //TODO: add custom end grass and remove STONE from this
                .anyMatch(currState -> EMUtils.isSoil(currState, EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS));
        else  //TODO: add custom end grass and remove STONE from this
            return EMUtils.isSoil(world.getBlockState(pos.down()), EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS);
    }

    @Override
    public void neighborChanged(IBlockState state, @Nonnull World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!correctBlockState(state).canPlaceBlockAt(world, pos)) {
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
        state.getValue(TREE_TYPE).grow(world, rand, pos, state);
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