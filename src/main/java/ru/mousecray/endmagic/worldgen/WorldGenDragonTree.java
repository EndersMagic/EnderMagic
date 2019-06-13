package ru.mousecray.endmagic.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static net.minecraft.block.BlockLog.EnumAxis.fromFacingAxis;
import static net.minecraft.block.BlockLog.LOG_AXIS;

public class WorldGenDragonTree extends WorldGenAbstractTree {

    private IBlockState enderLog = EMBlocks.enderLog.getDefaultState();
    private IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState();

    public WorldGenDragonTree(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return findDirection(worldIn, position).map(direction -> {

            enderLog = enderLog.withProperty(LOG_AXIS, fromFacingAxis(direction.getAxis()));

            int size = rand.nextInt(3) + 1;

            generateLog(worldIn, rand, position, direction, size);

            if (size < 3)
                generateSmallCrown(worldIn, rand, position, direction, size);
            else
                generateBigCrown(worldIn, rand, position, direction, size);

            return true;
        }).orElse(false);
    }

    private void generateBigCrown(World worldIn, Random rand, BlockPos position, EnumFacing direction, int size) {
        EnumFacing side = direction.rotateY();
        EnumFacing oppositeSide = side.getOpposite();

        for (int i = 0; i < size; i++) {
            BlockPos current = position.offset(direction, i);

            if (i == 0 || i == size - 1) {
                int i1 = min(size - i, 2);
                ImmutableList.of(side, oppositeSide, EnumFacing.UP, EnumFacing.DOWN)
                        .forEach(enumFacing -> generateLeaves(worldIn, rand, current, enumFacing, i1));
            }
        }
        if (size > 1)
            ImmutableList.of(
                    position.offset(side).offset(EnumFacing.UP),
                    position.offset(oppositeSide).offset(EnumFacing.UP),
                    position.offset(side).offset(EnumFacing.DOWN),
                    position.offset(oppositeSide).offset(EnumFacing.DOWN)
            ).forEach(pos ->
                    worldIn.setBlockState(pos, enderLeaves));
        worldIn.setBlockState(position.offset(direction, size), enderLeaves);
    }

    private void generateSmallCrown(World worldIn, Random rand, BlockPos position, EnumFacing direction, int size) {
        EnumFacing side = direction.rotateY();
        EnumFacing oppositeSide = side.getOpposite();
        for (int i = 0; i < size; i++) {
            int diameter = size + 1 - i;

            for (int y = -diameter; y <= diameter; y++) {
                BlockPos vertical = position.offset(EnumFacing.UP, y).offset(direction, i);
                for (int x = min(diameter - abs(y) + 1, diameter); x >= 1; x--) {
                    generateOneLeaves(worldIn, vertical.offset(side, x));
                    generateOneLeaves(worldIn, vertical.offset(oppositeSide, x));
                }
                if (y != 0)
                    generateOneLeaves(worldIn, vertical);
            }
        }
        BlockPos top = position.offset(direction, size);

        generateOneLeaves(worldIn, top);
        ImmutableList.of(side, oppositeSide, EnumFacing.UP, EnumFacing.DOWN)
                .forEach(enumFacing ->
                        generateOneLeaves(worldIn, top.offset(enumFacing)));

        if (rand.nextBoolean()) {
            ImmutableList.of(
                    top.offset(side).offset(EnumFacing.UP),
                    top.offset(oppositeSide).offset(EnumFacing.UP),
                    top.offset(side).offset(EnumFacing.DOWN),
                    top.offset(oppositeSide).offset(EnumFacing.DOWN)
            ).forEach(pos -> {
                if (rand.nextFloat() > 0.7)
                    worldIn.setBlockState(pos, enderLeaves);
            });
        }
    }

    private void generateOneLeaves(World worldIn, BlockPos position) {
        if (worldIn.getBlockState(position).getBlock() == Blocks.AIR)
            worldIn.setBlockState(position, enderLeaves);
    }

    private void generateLog(World worldIn, Random rand, BlockPos position, EnumFacing direction, int size) {
        for (int i = 0; i < size; i++) {
            BlockPos current = position.offset(direction, i);
            worldIn.setBlockState(current, enderLog);
        }
    }

    private void generateLeaves(World worldIn, Random rand, BlockPos position, EnumFacing side, int i) {
        for (int j = 1; j <= i; j++)
            worldIn.setBlockState(position.offset(side, j), enderLeaves);
        worldIn.setBlockState(position.offset(side, 1), enderLeaves);

    }

    public static Optional<EnumFacing> findDirection(World worldIn, BlockPos position) {
        return Arrays.stream(EnumFacing.HORIZONTALS)
                .filter(i -> worldIn.getBlockState(position.offset(i)).getBlock() == Blocks.END_STONE)
                .map(EnumFacing::getOpposite)
                .findFirst();
    }

    public void generateWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    }
}
