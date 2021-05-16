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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.blocks.base.BaseTreeBlock;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;
import ru.mousecray.endmagic.worldgen.trees.WorldGenEnderTree;
import ru.mousecray.endmagic.worldgen.trees.world.WorldGenDragonTreeWorld;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.minecraft.block.BlockSapling.SAPLING_AABB;
import static ru.mousecray.endmagic.util.EnderBlockTypes.TREE_TYPE;

public class EMSapling extends BaseTreeBlock implements IGrowable {

    public EMSapling() {
        super(Material.PLANTS);
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
        return worldIn.getBlockState(pos).getValue(TREE_TYPE) == EnderBlockTypes.EnderTreeType.DRAGON
                ? Arrays.stream(EnumFacing.HORIZONTALS)
                .map(pos::offset)
                .map(worldIn::getBlockState)
                //TODO: add custom end grass and remove STONE from this
                .anyMatch(state -> EMUtils.isSoil(state, EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS))
                //TODO: add custom end grass and remove STONE from this
                : EMUtils.isSoil(worldIn.getBlockState(pos.down()), EndSoilType.STONE, EndSoilType.DIRT, EndSoilType.GRASS);
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
            world.setBlockState(pos, state.withProperty(TREE_TYPE, EnderBlockTypes.EnderTreeType.values()[stack.getItemDamage()]));

    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        if (world.provider instanceof WorldProviderEnd && WorldGenDragonTreeWorld.isInCentralIsland(pos.getX() >> 4, pos.getZ() >> 4))
            return !ReflectionHelper.<Boolean, DragonFightManager>getPrivateValue(DragonFightManager.class, ((WorldProviderEnd) world.provider).getDragonFightManager(), "dragonKilled");
        else {
            AtomicBoolean dragonPowerSourceExists = new AtomicBoolean(false);
            WorldGenUtils.generateInAreaBreakly(pos.add(-5, -5, -5), pos.add(5, 5, 5), p -> {
                if (isDragonPowerSource(world, p)) {
                    dragonPowerSourceExists.set(true);
                    return false;
                } else
                    return true;
            });
            return dragonPowerSourceExists.get();
        }
    }

    private boolean isDragonPowerSource(World world, BlockPos p) {
        IBlockState blockState = world.getBlockState(p);
        Block block = blockState.getBlock();
        if (block == Blocks.DRAGON_EGG)
            return true;
        else if (block == Blocks.SKULL) {
            TileEntity tile = world.getTileEntity(p);
            if (tile instanceof TileEntitySkull)
                return ((TileEntitySkull) tile).getSkullType() == 5;
            else
                return false;
        } else
            return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        WorldGenEnderTree generator = state.getValue(TREE_TYPE).generator();
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