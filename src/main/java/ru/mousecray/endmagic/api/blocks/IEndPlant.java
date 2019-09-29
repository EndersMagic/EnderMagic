package ru.mousecray.endmagic.api.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEndPlant {
	default EndBlockType getBlockType() {
		return EndBlockType.WILD_PLANT;
	}
	
	default boolean isSoil(World world, BlockPos pos) {
		if(getBlockType() == EndBlockType.WILD_PLANT) {
	    	Block block = world.getBlockState(pos).getBlock();
	    	if (block instanceof IEndBlock) {
	    		EndBlockType type = ((IEndBlock)block).getBlockType();
	    		return block == Blocks.END_STONE || type == EndBlockType.GRASS;
	    	}
	    	else return false;
		}
		else if(getBlockType() == EndBlockType.CROP) {
	    	Block block = world.getBlockState(pos).getBlock();
	    	if (block instanceof IEndBlock) {
	    		EndBlockType type = ((IEndBlock)block).getBlockType();
	    		return type == EndBlockType.DIRT || type == EndBlockType.GRASS;
	    	}
	    	else return false;
		}
		else return false;
	}
}