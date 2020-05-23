package ru.mousecray.endmagic.worldgen.trees;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.EMUtils;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenNaturalTree extends WorldGenEnderTree {

    public static List<int[][]> branches = new ArrayList();
    public static List<int[][]> leaves = new ArrayList();

    public static final Vec3i areaRequirementsMin = new Vec3i(-3, 0, -3);
    public static final Vec3i areaRequirementsMax = new Vec3i(3, 9, 3);

    static {
        int[][] branch1 = new int[][]{{0, 0, 0}, {1, 0, 0}, {2, 1, 0}, {3, 1, 1}, {4, 2, 2}, {4, 2, 3}};
        int[][] branch2 = new int[][]{{0, 0, 0}, {1, 1, 0}, {2, 1, 1}, {3, 1, 2}, {4, 1, 3}, {4, 2, 4}};
        int[][] branch3 = new int[][]{{0, 0, 0}, {1, 1, 0}, {2, 1, 1}, {2, 1, 2}, {3, 2, 3}, {4, 3, 3}};
        int[][] branch4 = new int[][]{{0, 0, 0}, {1, 1, 0}, {2, 2, 1}, {2, 2, 2}, {3, 2, 3}, {4, 2, 4}};
        int[][] branch5 = new int[][]{{0, 0, 0}, {1, 0, 0}, {2, 1, 0}, {3, 1, 1}, {4, 2, 2}, {5, 2, 3}};
        int[][] branch6 = new int[][]{{0, 0, 0}, {1, 0, 1}, {2, 0, 1}, {3, 1, 1}, {4, 1, 2}, {5, 1, 3}};
        int[][] branch7 = new int[][]{{0, 0, 0}, {1, 0, -1}, {2, 1, -1}, {3, 1, 0}, {3, 1, 1},
                {2, 1, 2}};
        int[][] branch8 = new int[][]{{0, 0, 0}, {1, 0, 1}, {2, 0, 1}, {3, 0, 0}, {3, 1, -1},
                {3, 1, -2}};
        int[][] branch9 = new int[][]{{0, 0, 0}, {0, 0, -1}, {1, 0, -2}, {2, 1, -2}, {3, 1, -1},
                {4, 1, 0}};

        int[][] mainBranch = new int[5][3];
        for (int x = 0; x < 5; ++x) {
            int y = EMUtils.log2nlz(x * 3);
            mainBranch[x] = new int[]{x, y, 0};
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

        int[][] leaves1 = new int[][]{{0, -1, 0}, {0, -1, -1}, {0, 0, -1}, {0, 0, -2}, {0, 0, 1},
                {-1, 0, -1}, {-1, 0, 0}, {-1, 0, 1}, {1, 0, 1}, {0, 0, 0}, {1, 0, -1}, {1, 0, 0},
                {0, 1, 0}, {1, 1, 0}, {1, 1, 1}, {0, 1, -1}};
        int[][] leaves2 = new int[][]{{0, 0, 0}, {0, -1, 0}, {0, -1, -1}, {0, -1, 1}, {1, -1, 0},
                {-1, -1, 0}, {-1, -1, 1}, {0, 0, -1}, {0, 0, 1}, {-1, 0, -1}, {-1, 0, 0}, {-1, 0, 1},
                {1, 0, 0}, {1, 0, 1}, {0, 1, 0}, {0, 1, 1}, {-1, 1, 1}};
        int[][] leaves3 = new int[][]{{0, -1, 0}, {0, -1, 1}, {1, -1, 0}, {1, -1, -1}, {1, 0, -1},
                {1, 0, 0}, {1, 0, 1}, {0, 0, -1}, {0, 0, 0}, {0, 0, 1}, {-1, 0, -1}, {-1, 0, 0},
                {-1, 0, 1}, {1, 1, 0}, {-1, 1, 0}, {0, 0, -1}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}};

        leaves.add(leaves1);
        leaves.add(leaves2);
        leaves.add(leaves3);
    }

    private IBlockState enderLog = EMBlocks.enderLog.getDefaultState().withProperty(EnderBlockTypes.TREE_TYPE, EnderBlockTypes.EnderTreeType.NATURAL);
    private IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState().withProperty(EnderBlockTypes.TREE_TYPE,
            EnderBlockTypes.EnderTreeType.NATURAL);

    public WorldGenNaturalTree(boolean notify) {
        super(notify, areaRequirementsMin, areaRequirementsMax);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        if (canGenerateThereAvaiable(world, position)) {
            int logHeight = 7;
            EnumAxis defaultAxis = EnumAxis.NONE;
            Rotation rot1 = EMUtils.getRandomRotation(world.rand);
            Rotation rot2 = EMUtils.getUnrepeatRotation(world.rand, rot1);
            Rotation rot3 = EMUtils.getUnrepeatRotation(world.rand, rot1, rot2);
            int[][] branch1 = EMUtils.rotateForY(branches.get(world.rand.nextInt(9)), new int[]{0, 0, 0}, rot1);
            int[][] branch2 = EMUtils.rotateForY(branches.get(world.rand.nextInt(9)), new int[]{0, 0, 0}, rot2);
            int[][] mainBranch = EMUtils.rotateForY(branches.get(9), new int[]{0, 0, 0}, rot3);
            BlockPos[] lastPos1, lastPos2, lastPos3;
            for (int h = 0; h < logHeight; ++h) world.setBlockState(position.up(h), enderLog);

            lastPos1 = generateBranch(world, branch1, logHeight / 3, position, defaultAxis);
            lastPos2 = generateBranch(world, branch2, logHeight / 3 * 2, position, defaultAxis);
            lastPos3 = generateBranch(world, mainBranch, logHeight - 2, position, defaultAxis);

            generateLeaves(world, leaves.get(world.rand.nextInt(3)), lastPos1[0]);
            generateLeaves(world, leaves.get(world.rand.nextInt(3)), lastPos2[0]);
            generateLeaves(world, leaves.get(world.rand.nextInt(3)), lastPos3[0]);

            if (lastPos1.length > 1) {
                setBlockAndNotifyAdequately(world, lastPos1[1].down(), EMBlocks.chrysVine.getDefaultState());
                setBlockAndNotifyAdequately(world, lastPos1[1].down(2), EMBlocks.chrysFlower.getDefaultState());
            }
            if (lastPos2.length > 1) {
                setBlockAndNotifyAdequately(world, lastPos2[1].down(), EMBlocks.chrysVine.getDefaultState());
                setBlockAndNotifyAdequately(world, lastPos2[1].down(2), EMBlocks.chrysVine.getDefaultState());
                setBlockAndNotifyAdequately(world, lastPos2[1].down(3), EMBlocks.chrysFruit.getDefaultState());
            }
            if (lastPos3.length > 1) {
                setBlockAndNotifyAdequately(world, lastPos3[1].down(), EMBlocks.chrysVine.getDefaultState());
                setBlockAndNotifyAdequately(world, lastPos3[1].down(2), EMBlocks.chrysVine.getDefaultState());
                setBlockAndNotifyAdequately(world, lastPos3[1].down(3), EMBlocks.chrysFlower.getDefaultState());
            }
            return true;
        } else return false;
    }

    private BlockPos[] generateBranch(World world, int[][] branch, int logHeight, BlockPos pos0, EnumAxis axis) {
        BlockPos lastPos = BlockPos.ORIGIN;
        BlockPos middlePos = BlockPos.ORIGIN;
        for (int c = 0; c < branch.length; ++c) {
            int[] currBranch = branch[c];
            BlockPos currPos = pos0.up(logHeight).add(currBranch[0], currBranch[1], currBranch[2]);
            setBlockAndNotifyAdequately(world, currPos, enderLog.withProperty(BlockLog.LOG_AXIS, axis));
            if (c == branch.length - 1) lastPos = currPos;
            else if (c == branch.length / 2) middlePos = currPos;
        }
        return world.rand.nextInt(20) == 0 ? new BlockPos[]{lastPos, middlePos} : new BlockPos[]{lastPos};
    }

    private void generateLeaves(World world, int[][] leaves, BlockPos lastPos) {
        for (int l = 0; l < leaves.length; ++l) {
            int[] currLeaves = leaves[l];
            BlockPos currPos = lastPos.add(currLeaves[0], currLeaves[1], currLeaves[2]);
            if (world.getBlockState(currPos).getMaterial() != Material.WOOD) setBlockAndNotifyAdequately(world, currPos, enderLeaves);
        }
    }
}