package ru.mousecray.endmagic.util;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;

public class EMUtils {

	public static EnumFacing getNegativeFacing(EnumFacing facing) {
		switch (facing) {
			case UP: return EnumFacing.DOWN;
			case DOWN: return EnumFacing.UP;
			case NORTH: return EnumFacing.SOUTH;
			case SOUTH: return EnumFacing.NORTH;
			case WEST: return EnumFacing.EAST;
			case EAST: default: return EnumFacing.WEST;
		}
	}
	
	public static boolean isSoil(World world, BlockPos pos, EndSoilType... types) {
		boolean isSoil = false;
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof IEndSoil) {
			if (types.length > 0) {
				EndSoilType type = ((IEndSoil)block).getSoilType();
				for (int i = 0; i < types.length; ++i) isSoil = type == types[i];
			}
			else isSoil = true;
		}
		return isSoil;
	}
}