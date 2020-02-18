package ru.mousecray.endmagic.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class WorldGenNaturalTree extends WorldGenAbstractTree {

	public static List<int[][]> branches = new ArrayList();
	public static List<int[][]> leaves = new ArrayList();

	static {
		int[][] branch1 = new int[][] { { 0, 0, 0 }, { 1, 0, 0 }, { 2, 1, 0 }, { 3, 1, 1 }, { 4, 2, 2 }, { 4, 2, 3 } };
		int[][] branch2 = new int[][] { { 0, 0, 0 }, { 1, 1, 0 }, { 2, 1, 1 }, { 3, 1, 2 }, { 4, 1, 3 }, { 4, 2, 4 } };
		int[][] branch3 = new int[][] { { 0, 0, 0 }, { 1, 1, 0 }, { 2, 1, 1 }, { 2, 1, 2 }, { 3, 2, 3 }, { 4, 3, 3 } };
		int[][] branch4 = new int[][] { { 0, 0, 0 }, { 1, 1, 0 }, { 2, 2, 1 }, { 2, 2, 2 }, { 3, 2, 3 }, { 4, 2, 4 } };
		int[][] branch5 = new int[][] { { 0, 0, 0 }, { 1, 0, 0 }, { 2, 1, 0 }, { 3, 1, 1 }, { 4, 2, 2 }, { 5, 2, 3 } };
		int[][] branch6 = new int[][] { { 0, 0, 0 }, { 1, 0, 1 }, { 2, 0, 1 }, { 3, 1, 1 }, { 4, 1, 2 }, { 5, 1, 3 } };
		int[][] branch7 = new int[][] { { 0, 0, 0 }, { 1, 0, -1 }, { 2, 1, -1 }, { 3, 1, 0 }, { 3, 1, 1 },
				{ 2, 1, 2 } };
		int[][] branch8 = new int[][] { { 0, 0, 0 }, { 1, 0, 1 }, { 2, 0, 1 }, { 3, 0, 0 }, { 3, 1, -1 },
				{ 3, 1, -2 } };
		int[][] branch9 = new int[][] { { 0, 0, 0 }, { 0, 0, -1 }, { 1, 0, -2 }, { 2, 1, -2 }, { 3, 1, -1 },
				{ 4, 1, 0 } };

		int[][] mainBranch = new int[5][3];
		for (int x = 0; x < 5; ++x) {
			int y = EMUtils.log2nlz(x * 3);
			mainBranch[x] = new int[] { x, y, 0 };
		}

		branches.add(branch1);
		branches.add(branch2);
		branches.add(branch3);
		branches.add(branch4);
		branches.add(branch5);
		branches.add(branch6);
		branches.add(branch7);
		branches.add(branch8);
		branches.add(branch9);

		branches.add(mainBranch);

		int[][] leaves1 = new int[][] { { 0, -1, 0 }, { 0, -1, -1 }, { 0, 0, -1 }, { 0, 0, -2 }, { 0, 0, 1 },
				{ -1, 0, -1 }, { -1, 0, 0 }, { -1, 0, 1 }, { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, -1 }, { 1, 0, 0 },
				{ 0, 1, 0 }, { 1, 1, 0 }, { 1, 1, 1 }, { 0, 1, -1 } };
		int[][] leaves2 = new int[][] { { 0, 0, 0 }, { 0, -1, 0 }, { 0, -1, -1 }, { 0, -1, 1 }, { 1, -1, 0 },
				{ -1, -1, 0 }, { -1, -1, 1 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, -1 }, { -1, 0, 0 }, { -1, 0, 1 },
				{ 1, 0, 0 }, { 1, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { -1, 1, 1 } };
		int[][] leaves3 = new int[][] { { 0, -1, 0 }, { 0, -1, 1 }, { 1, -1, 0 }, { 1, -1, -1 }, { 1, 0, -1 },
				{ 1, 0, 0 }, { 1, 0, 1 }, { 0, 0, -1 }, { 0, 0, 0 }, { 0, 0, 1 }, { -1, 0, -1 }, { -1, 0, 0 },
				{ -1, 0, 1 }, { 1, 1, 0 }, { -1, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 } };

		leaves.add(leaves1);
		leaves.add(leaves2);
		leaves.add(leaves3);
	}

	private IBlockState enderLog = EMBlocks.enderLog.getDefaultState().withProperty(EMBlocks.enderLog.getVariantType(),
			EnderBlockTypes.EnderTreeType.NATURAL);
	private IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState()
			.withProperty(EMBlocks.enderLeaves.getVariantType(), EnderBlockTypes.EnderTreeType.NATURAL);

	public WorldGenNaturalTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		if (EMUtils.isSoil(world.getBlockState(position), true, false, EndSoilType.GRASS, EndSoilType.DIRT)) {
			int logHeight = 7;
			if (!checkAirBound(world, position, logHeight)) return false;
			EnumAxis defaultAxis = EnumAxis.NONE;
			Rotation rot1 = EMUtils.getRandomRotation(world.rand);
			Rotation rot2 = EMUtils.getUnrepeatRotation(world.rand, rot1);
			Rotation rot3 = EMUtils.getUnrepeatRotation(world.rand, rot1, rot2);
			int[][] branch1 = EMUtils.rotateForY(branches.get(world.rand.nextInt(9)), new int[] { 0, 0, 0 }, rot1);
			int[][] branch2 = EMUtils.rotateForY(branches.get(world.rand.nextInt(9)), new int[] { 0, 0, 0 }, rot2);
			int[][] mainBranch = EMUtils.rotateForY(branches.get(9), new int[] { 0, 0, 0 }, rot3);
			BlockPos[] lastPos1, lastPos2, lastPos3;
			for (int h = 0; h < logHeight; ++h) { world.setBlockState(position.up(h + 1), enderLog); }
			world.setBlockState(position.add(-1, 1, 0), enderLog.withProperty(BlockLog.LOG_AXIS, EnumAxis.Y));
			world.setBlockState(position.add(1, 1, 0), enderLog.withProperty(BlockLog.LOG_AXIS, EnumAxis.Y));
			world.setBlockState(position.add(0, 1, 1), enderLog.withProperty(BlockLog.LOG_AXIS, EnumAxis.Y));
			world.setBlockState(position.add(0, 1, -1), enderLog.withProperty(BlockLog.LOG_AXIS, EnumAxis.Y));
			if (world.rand.nextBoolean())
				world.setBlockState(position.add(-1, 1, 1), enderLog.withProperty(BlockLog.LOG_AXIS, EnumAxis.Z));
			if (world.rand.nextBoolean())
				world.setBlockState(position.add(1, 1, -1), enderLog.withProperty(BlockLog.LOG_AXIS, EnumAxis.Z));

			lastPos1 = generateBranch(world, branch1, logHeight / 3, position, defaultAxis);
			lastPos2 = generateBranch(world, branch2, logHeight / 3 * 2, position, defaultAxis);
			lastPos3 = generateBranch(world, mainBranch, logHeight - 2, position, defaultAxis);

			generateLeaves(world, leaves.get(world.rand.nextInt(3)), lastPos1[0]);
			generateLeaves(world, leaves.get(world.rand.nextInt(3)), lastPos2[0]);
			generateLeaves(world, leaves.get(world.rand.nextInt(3)), lastPos3[0]);

			if (lastPos1.length > 1) {
				world.setBlockState(lastPos1[1].down(), EMBlocks.chrysVine.getDefaultState());
				world.setBlockState(lastPos1[1].down(2), EMBlocks.chrysFlower.getDefaultState());
			}
			if (lastPos2.length > 1) {
				world.setBlockState(lastPos2[1].down(), EMBlocks.chrysVine.getDefaultState());
				world.setBlockState(lastPos2[1].down(2), EMBlocks.chrysVine.getDefaultState());
				world.setBlockState(lastPos2[1].down(3), EMBlocks.chrysFruit.getDefaultState());
			}
			if (lastPos3.length > 1) {
				world.setBlockState(lastPos3[1].down(), EMBlocks.chrysVine.getDefaultState());
				world.setBlockState(lastPos3[1].down(2), EMBlocks.chrysVine.getDefaultState());
				world.setBlockState(lastPos3[1].down(3), EMBlocks.chrysFlower.getDefaultState());
			}
			return true;
		}
		else return false;
	}

	private boolean checkAirBound(World world, BlockPos position, int logHeight) {
		boolean flag = true;
		if (position.getY() + 1 >= 1 && position.getY() + logHeight + 2 <= world.getHeight()) {
			for (int y = position.getY() + 1; y <= position.getY() + 2 + logHeight; ++y) {
				int k = 1;
				if (y == position.getY() + 2) k = 2;
				else k = 5;

				BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

				for (int x = position.getX() - k; x <= position.getX() + k && flag; ++x) {
					for (int z = position.getZ() - k; z <= position.getZ() + k && flag; ++z) {
						if (!isReplaceable(world, mutablePos.setPos(x, y, z))) flag = false;
					}
				}
			}
		}
		else return false;
		return flag;
	}

	private BlockPos[] generateBranch(World world, int[][] branch, int logHeight, BlockPos pos0, EnumAxis axis) {
		BlockPos lastPos = BlockPos.ORIGIN;
		BlockPos middlePos = BlockPos.ORIGIN;
		for (int c = 0; c < branch.length; ++c) {
			int[] currBranch = branch[c];
			BlockPos currPos = pos0.up(logHeight + 1).add(currBranch[0], currBranch[1], currBranch[2]);
			world.setBlockState(currPos, enderLog.withProperty(BlockLog.LOG_AXIS, axis));
			if (c == branch.length - 1) lastPos = currPos;
			else if (c == branch.length / 2) middlePos = currPos;
		}
		return world.rand.nextInt(20) == 0 ? new BlockPos[] { lastPos, middlePos } : new BlockPos[] { lastPos };
	}

	private void generateLeaves(World world, int[][] leaves, BlockPos lastPos) {
		for (int l = 0; l < leaves.length; ++l) {
			int[] currLeaves = leaves[l];
			BlockPos currPos = lastPos.add(currLeaves[0], currLeaves[1], currLeaves[2]);
			if (world.getBlockState(currPos).getMaterial() != Material.WOOD) world.setBlockState(currPos, enderLeaves);
		}
	}
}