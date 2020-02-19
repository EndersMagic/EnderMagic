package ru.mousecray.endmagic.worldgen.trees;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static net.minecraft.init.Blocks.AIR;
import static ru.mousecray.endmagic.util.worldgen.WorldGenUtils.generateInArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.init.EMBlocks;

public class WorldGenDragonTree extends WorldGenEnderTree {
    public WorldGenDragonTree(boolean notify) {
        super(notify,null,null);
    }

    private IBlockState enderLog = EMBlocks.enderLog.getDefaultState();
    private IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState();

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return generate(worldIn, rand, position, false, new HashSet<>());
    }

    public boolean generate(World world, Random random, BlockPos pos, boolean check, Set<BlockPos> alreadyChecked) {
        EnumFacing direction = logDirection(world, pos).getOpposite();
        BlockLog.EnumAxis value = BlockLog.EnumAxis.fromFacingAxis(direction.getAxis());
        if (value != BlockLog.EnumAxis.Y) {
        	BlockPos of = pos.offset(direction);
            if (EMBlocks.enderSapling.canPlaceBlockAt(world, pos) && (check ? aroundBlocks(world, pos, AIR, 77, alreadyChecked) : true)) {

                int lvl = 2 + random.nextInt(3);
                for (int i = 0; i < lvl; i++)
                    world.setBlockState(pos.offset(direction, i), enderLog.withProperty(LOG_AXIS, value), 18);

                generateLeavesAround(world, pos.offset(direction), lvl);
                return true;
            }
        }
        return false;
    }

    private void generateLeavesAround(World world, BlockPos pos, int lvl) {
        spreadOut(world, pos, enderLeaves, AIR, lvl, 0.3);
        spreadOut(world, pos, enderLeaves, AIR, lvl, 0.3);
        spreadOut(world, pos, enderLeaves, AIR, lvl, 0.3);
    }

    public static boolean[][][] spreadOut(World world, BlockPos centerPos, IBlockState block, Block air, int lvl, double chance) {
        HashSet<BlockPos> alreadyChecked = new HashSet<>();
        List<Pair<BlockPos, IBlockState>> setLeaves = new ArrayList<>();

        int areaSize = lvl * 2 + 1;
        boolean[][][] marks = new boolean[areaSize][areaSize][areaSize];
        BlockPos start = new BlockPos(centerPos.getX() - lvl, centerPos.getY() - lvl, centerPos.getZ() - lvl);
        generateInArea(
                start,
                new BlockPos(centerPos.getX() + lvl, centerPos.getY() + lvl, centerPos.getZ() + lvl),
                pos -> {
                    if (pos.distanceSq(centerPos) < lvl * lvl) {
                        if (world.getBlockState(pos).getBlock() == air && aroundBlocks(world, pos, air, 1, alreadyChecked)) {
                            Arrays.stream(EnumFacing.values())
                                    .map(pos::offset)
                                    .filter(i -> world.getBlockState(i).getBlock() == air)
                                    .map(i -> {
                                        if (getMark(marks, i, start)) {
                                            return Pair.of(i, block);
                                        } else {
                                            boolean willBeAir = world.rand.nextFloat() > chance;
                                            if (willBeAir) {
                                                Arrays.stream(EnumFacing.values()).map(i::offset).forEach(markPos -> setMark(marks, markPos, start));
                                                return Pair.of(i, air.getDefaultState());
                                            } else
                                                return Pair.of(i, block);
                                        }
                                    })
                                    .forEach(setLeaves::add);
                        }
                        alreadyChecked.clear();
                    }
                }
        );
        setLeaves.forEach(i -> world.setBlockState(i.getLeft(), i.getRight(), 18));
        return marks;
    }


    private static void setMark(boolean[][][] marks, BlockPos markPos, BlockPos start) {
        int x = markPos.getX() - start.getX();
        int y = markPos.getY() - start.getY();
        int z = markPos.getZ() - start.getZ();

        if (x > 0 && x < marks.length)
            if (y >= 0 && y < marks[x].length)
                if (z >= 0 && z < marks[x][y].length)
                    marks[x][y][z] = true;
    }

    private static boolean getMark(boolean[][][] marks, BlockPos markPos, BlockPos start) {
        int x = markPos.getX() - start.getX();
        int y = markPos.getY() - start.getY();
        int z = markPos.getZ() - start.getZ();

        if (x > 0 && x < marks.length)
            if (y >= 0 && y < marks[x].length)
                if (z >= 0 && z < marks[x][y].length)
                    return marks[x][y][z];
        return false;
    }

    private EnumFacing logDirection(World world, BlockPos pos) {
        return Arrays.stream(EnumFacing.values())
                        .filter((i -> world.getBlockState(pos.offset(i)).getBlock() == AIR))
                        .findAny()
                        .orElse(EnumFacing.UP);
    }

    public static boolean aroundBlocks(World world, BlockPos pos, Block air, int n, Set<BlockPos> alreadyChecked) {
        alreadyChecked.add(pos);
        if (n == 0)
            return true;
        else {
            return Arrays.stream(EnumFacing.values())
                    .map(pos::offset)
                    .filter(i -> !alreadyChecked.contains(i))
                    .anyMatch(i -> world.getBlockState(i).getBlock() == air && aroundBlocks(world, i, air, n - 1, alreadyChecked));
        }
    }
}
