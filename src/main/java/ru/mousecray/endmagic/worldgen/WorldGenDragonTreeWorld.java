package ru.mousecray.endmagic.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Stream;

import static net.minecraft.init.Blocks.*;

public class WorldGenDragonTreeWorld {
    private WorldGenDragonTree generator = new WorldGenDragonTree(true);

    private int centralIslandSize = 9;
    //3;minecraft:bedrock,2*minecraft:dirt,minecraft:end_portal;1;village

    public void generateWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if (chunkX * chunkX + chunkZ * chunkZ < centralIslandSize * centralIslandSize) {
            if (!world.getChunkFromChunkCoords(chunkX, chunkZ).isEmpty()) {
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                int startX = chunkX << 4;
                int startZ = chunkZ << 4;
                Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
                for (int x = startX; x < startX + 16; x++) {
                    for (int z = startZ; z < startZ + 16; z++) {
                        for (int y = 30; y < 50; y++) {
                            pos.setPos(x, y, z);
                            if (chunk.getBlockState(pos).getBlock() == END_STONE && aroundBlocks(chunk, pos, AIR, 4, new HashSet<>())) {
                                chunk.setBlockState(pos, GLASS.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean aroundBlocks(Chunk chunk, BlockPos pos, Block air, int n, HashSet<BlockPos> alreadyChecked) {
        alreadyChecked.add(pos);
        if (n == 0)
            return true;
        else {
            return Arrays.stream(EnumFacing.values())
                    .map(pos::offset)
                    .filter(i -> chunkContains(chunk, i))
                    .filter(i -> !alreadyChecked.contains(i))
                    .anyMatch(i -> chunk.getBlockState(i).getBlock() == air && aroundBlocks(chunk, i, air, n - 1, alreadyChecked));
        }
    }

    private static boolean chunkContains(Chunk chunk, BlockPos pos) {
        return chunk.getPos().getXStart() <= pos.getX() && chunk.getPos().getXEnd() >= pos.getX() &&
                chunk.getPos().getZStart() <= pos.getZ() && chunk.getPos().getZEnd() >= pos.getZ();
    }


    public static Stream<BlockPos> walkAround(World world, BlockPos current, EnumFacing dirrection, EnumFacing up, int step) {
        return Stream.iterate(new TraverseState(current, dirrection, up, 0),
                state -> {
                    if (world.getBlockState(state.current.offset(state.ground)).getBlock() == END_STONE) {
                        BlockPos next = state.current.offset(state.dirrection);
                        if (world.isAirBlock(next))
                            return new TraverseState(next, state.dirrection, state.up, state.stepCount + 1);
                        else {
                            if (world.isAirBlock(state.current.offset(state.up)))
                                return new TraverseState(state.current.offset(state.up), state.up, state.dirrection.getOpposite(), state.stepCount + 1);
                            else
                                return new TraverseState(state.current.offset(state.dirrection.getOpposite()), state.dirrection.getOpposite(), state.ground, state.stepCount + 1);
                        }
                    } else
                        return new TraverseState(state.current.offset(state.ground), state.ground, state.dirrection, state.stepCount + 1);
                }).map(TraverseState::getCurrent);
    }

    private static class TraverseState {
        public BlockPos getCurrent() {
            return current;
        }

        public final BlockPos current;
        public final EnumFacing dirrection;
        public final EnumFacing up;
        public final int stepCount;
        public final EnumFacing ground;

        public TraverseState(BlockPos current, EnumFacing dirrection, EnumFacing up, int stepCount) {
            this.current = current;
            this.dirrection = dirrection;
            this.up = up;
            this.stepCount = stepCount;
            ground = up.getOpposite();
        }
    }
}
