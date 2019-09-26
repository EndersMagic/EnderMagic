package ru.mousecray.endmagic.util;

import net.minecraft.util.EnumFacing;

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
}