package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.blocks.BlockWithTile;
import ru.mousecray.endmagic.teleport.TeleportUtils;
import ru.mousecray.endmagic.tileentity.portal.TilePortal;

import java.util.Random;

public class BlockPortal extends BlockWithTile<TilePortal> {
    public BlockPortal() {
        super(Material.PORTAL);
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote)
            TeleportUtils.teleportToBlockLocation(entityIn, tile(worldIn, pos).distination);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePortal();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer() {
    	return BlockRenderLayer.TRANSLUCENT;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        return !iblockstate.isOpaqueCube() && block != Blocks.END_GATEWAY;
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.BLACK;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
