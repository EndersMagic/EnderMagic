package ru.mousecray.endmagic.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

public class BlockBushWithTile<A extends TileEntity> extends BlockBush {

	protected BlockBushWithTile() {
		this.hasTileEntity = true;
	}

	protected boolean isInvalidNeighbor(World world, BlockPos pos, EnumFacing facing) {
		return world.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS;
	}

	protected boolean hasInvalidNeighbor(World world, BlockPos pos) {
		return this.isInvalidNeighbor(world, pos, EnumFacing.NORTH)
				|| this.isInvalidNeighbor(world, pos, EnumFacing.SOUTH)
				|| this.isInvalidNeighbor(world, pos, EnumFacing.WEST)
				|| this.isInvalidNeighbor(world, pos, EnumFacing.EAST);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state,
			@Nullable TileEntity te, ItemStack stack) {
		if (te instanceof IWorldNameable && ((IWorldNameable) te).hasCustomName()) {
			player.addStat(StatList.getBlockStats(this));
			player.addExhaustion(0.005F);

			if (world.isRemote) { return; }

			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			Item item = this.getItemDropped(state, world.rand, i);

			if (item == Items.AIR) { return; }

			ItemStack itemstack = new ItemStack(item, this.quantityDropped(world.rand));
			itemstack.setStackDisplayName(((IWorldNameable) te).getName());
			spawnAsEntity(world, pos, itemstack);
		}
		else {
			super.harvestBlock(world, player, pos, state, (TileEntity) null, stack);
		}
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	
    public A tile(World world, BlockPos pos) {
        return (A) world.getTileEntity(pos);
    }
}