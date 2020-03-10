package ru.mousecray.endmagic.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.api.blocks.IEndSoil;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMItems;

public class EMUtils {

	public static EnumFacing getNegativeFacing(EnumFacing facing) {
		switch (facing) {
		case UP:
			return EnumFacing.DOWN;
		case DOWN:
			return EnumFacing.UP;
		case NORTH:
			return EnumFacing.SOUTH;
		case SOUTH:
			return EnumFacing.NORTH;
		case WEST:
			return EnumFacing.EAST;
		case EAST:
		default:
			return EnumFacing.WEST;
		}
	}

	public static Axis getAxisRotationForY(Axis startAxis, Rotation rotate) {
		switch (rotate) {
		case CLOCKWISE_90:
		case COUNTERCLOCKWISE_90:
			return startAxis == Axis.X ? Axis.Z : Axis.X;
		case CLOCKWISE_180:
		case NONE:
		default:
			return startAxis;
		}
	}

    public static boolean isSoil(IBlockState state, boolean onlyVanillaBlock, boolean needSupportBonemeal, EndSoilType... filterTypes) {
        Block block = state.getBlock();
        ImmutableSet<EndSoilType> endSoilTypes = ImmutableSet.copyOf(filterTypes);
        return block == Blocks.END_STONE ||
                (!onlyVanillaBlock && block instanceof IEndSoil &&
                        (!needSupportBonemeal || ((IEndSoil) block).canUseBonemeal()) &&
                        (filterTypes.length==0 || endSoilTypes.contains(((IEndSoil) block).getSoilType())));
    }

    public static IBlockState getBonemealCrops(IBlockState block2, Random rand, EntityPlayer entityPlayer, IBlockState block21) {
        if (block2.getBlock() == Blocks.END_STONE)
            return EMBlocks.enderTallgrass.getDefaultState();
        else if (isSoil(block2, false, true))
            return ((IEndSoil) block2.getBlock()).getBonemealCrops(rand, entityPlayer, block21);
        else
            return Blocks.AIR.getDefaultState();
    }

	public static boolean isEnderArrow(ItemStack stack) {
		return stack.getItem() == EMItems.enderArrow;
	}

	public static boolean isArrow(ItemStack stack) {
		return stack.getItem() instanceof ItemArrow;
	}

	public static BlockPos getDownGround(World world, BlockPos pos, int bound) {
		return world.isAirBlock(pos) && bound > 0 ? getDownGround(world, pos.down(), --bound) : pos;
	}

	public static int logBase(double x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}

	public static int log2nlz(int bits) {
		if (bits == 0) return 0;
		return 31 - Integer.numberOfLeadingZeros(bits);
	}

	public static BlockPos intToPos(int[] ints) {
		return new BlockPos(ints[0], ints[1], ints[2]);
	}

	public static int[] posToInt(BlockPos pos) {
		return new int[] { pos.getX(), pos.getY(), pos.getZ() };
	}

	public static int[][] rotateForY(int[][] positions, int[] pos0, Rotation rotation) {
		switch (rotation) {
		case COUNTERCLOCKWISE_90: {
			for (int s = 0; s < positions.length; ++s) {
				int[] loaded = positions[s];
				positions[s] = new int[] { pos0[0] + loaded[2] - pos0[2], loaded[1], pos0[2] - loaded[0] + pos0[0] };
			}
			break;
		}
		case CLOCKWISE_90:
			for (int s = 0; s < positions.length; ++s) {
				int[] loaded = positions[s];
				positions[s] = new int[] { pos0[0] - loaded[2] + pos0[2], loaded[1], pos0[2] + loaded[0] - pos0[0] };
			}
			break;
		case CLOCKWISE_180:
			for (int s = 0; s < positions.length; ++s) {
				int[] loaded = positions[s];
				positions[s] = new int[] { 2 * pos0[0] - loaded[0], loaded[1], 2 * pos0[2] - loaded[2] };
			}
			break;
		case NONE:
		default:
			break;
		}
		return positions;
	}

	public static List<BlockPos> rotateForY(List<BlockPos> positions, BlockPos pos0, Rotation rotation) {
		switch (rotation) {
		case COUNTERCLOCKWISE_90: {
			positions.forEach(loaded -> loaded = new BlockPos(pos0.getX() + loaded.getZ() - pos0.getZ(), loaded.getY(),
					pos0.getZ() - loaded.getX() + pos0.getX()));
			break;
		}
		case CLOCKWISE_90:
			positions.forEach(loaded -> loaded = new BlockPos(pos0.getX() - loaded.getZ() + pos0.getZ(), loaded.getY(),
					pos0.getZ() + loaded.getX() - pos0.getX()));
			break;
		case CLOCKWISE_180:
			positions.forEach(loaded -> loaded = new BlockPos(2 * pos0.getX() - loaded.getX(), loaded.getY(),
					2 * pos0.getZ() - loaded.getZ()));
			break;
		case NONE:
		default:
			break;
		}
		return positions;
	}

	public static Rotation getUnrepeatRotation(Random rand, Rotation... rots) {
		List<Rotation> accessedRots = new ArrayList(Arrays.asList(Rotation.values()));
		accessedRots.removeAll(new ArrayList(Arrays.asList(rots)));
		if (accessedRots.isEmpty()) return getRandomRotation(rand);
		return accessedRots.get(rand.nextInt(accessedRots.size()));
	}

	public static Rotation getRandomRotation(Random rand) {
		int r = rand.nextInt(4);
		return r == 0 ? Rotation.CLOCKWISE_90
				: r == 1 ? Rotation.CLOCKWISE_180 : r == 2 ? Rotation.COUNTERCLOCKWISE_90 : Rotation.NONE;
	}
}