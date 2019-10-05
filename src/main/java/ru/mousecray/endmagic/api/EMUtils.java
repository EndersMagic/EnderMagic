package ru.mousecray.endmagic.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.init.EMItems;

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
	
	public static boolean isSoil(World world, BlockPos pos, boolean vanillaEnd, EndSoilType... types) {
		boolean isSoil = false;
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof IEndSoil) {
			if (types.length > 0) {
				EndSoilType type = ((IEndSoil)block).getSoilType();
				for (int i = 0; i < types.length; ++i) isSoil = type == types[i];
			}
			else isSoil = true;
		}
		else if (vanillaEnd) isSoil = block == Blocks.END_STONE;
		return isSoil;
	}
	
	public static List<BlockPos> isSoil(World world, BlockPos posStart, BlockPos posEnd, boolean vanillaEnd, EndSoilType... types) {
		List<BlockPos> existPos = new ArrayList();
		int xS = posStart.getX();
		int yS = posStart.getY();
		int zS = posStart.getZ();
		for (; xS < posEnd.getX(); ++xS) {
			for (; yS < posEnd.getY(); ++yS) {
				for (; zS < posEnd.getZ(); ++zS) {
					BlockPos donePos = new BlockPos(xS, yS, zS);
					if (isSoil(world, donePos, vanillaEnd, types)) existPos.add(donePos);
				}
			}
		}
		return existPos;
	}
	
    public static boolean isEnderArrow(ItemStack stack) {
        return stack.getItem() == EMItems.enderArrow;
    }

    public static boolean isArrow(ItemStack stack) {
        return stack.getItem() instanceof ItemArrow;
    }
}