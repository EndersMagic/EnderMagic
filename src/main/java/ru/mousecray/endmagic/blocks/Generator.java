package ru.mousecray.endmagic.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Generator extends GeneratorBase {

	public Generator() {
		super("generator");
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
		if(((Integer)state.getValue(layers)).intValue() > 1) {
			world.setBlockState(pos, Blocks.WATER.getDefaultState());
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote)
		if(player.getHeldItem(hand).getItem() == Items.WATER_BUCKET && ((Integer)state.getValue(layers)).intValue() < 5) {
			world.setBlockState(pos, getDefaultState().withProperty(layers, ((Integer)state.getValue(layers)).intValue() + 1));
			if(!player.isCreative()) player.setHeldItem(hand, new ItemStack(Items.BUCKET));
		}
		return true;
	}
}
