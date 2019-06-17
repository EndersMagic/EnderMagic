package ru.mousecray.endmagic.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static net.minecraft.init.Blocks.AIR;
import static net.minecraft.init.Blocks.END_STONE;

public class WorldGenDragonTreeWorld {
    private WorldGenDragonTree generator = new WorldGenDragonTree(true);

    private IBlockState enderLog = EMBlocks.enderLog.getDefaultState();
    private IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState();

    private int centralIslandSize = 9;
    //3;minecraft:bedrock,2*minecraft:dirt,minecraft:end_portal;1;

    public void generateWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkX * chunkX + chunkZ * chunkZ < centralIslandSize * centralIslandSize) {
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            if (!chunk.isEmpty()) {
                int startX = chunkX << 4;
                int startZ = chunkZ << 4;
                HashSet<BlockPos> alreadyChecked = new HashSet<>();
                generateInArea(
                        new BlockPos(startX, 30, startZ),
                        new BlockPos(startX + 15, 50, startZ + 15),
                        pos -> {
                            if (chunk.getBlockState(pos).getBlock() == END_STONE && aroundBlocks(chunk, pos, AIR, 4, alreadyChecked)) {
                                if (random.nextInt(40) == 0) {
                                    EnumFacing direction = logDirection(chunk, pos).getOpposite();
                                    BlockLog.EnumAxis value = BlockLog.EnumAxis.fromFacingAxis(direction.getAxis());
                                    //if (value != BlockLog.EnumAxis.Y)
                                    chunk.setBlockState(pos, enderLog.withProperty(LOG_AXIS, value));
                                    chunk.setBlockState(pos.offset(direction), enderLog.withProperty(LOG_AXIS, value));

                                    generateLeaveaAround(chunk, random, pos.offset(direction));
                                }

                            }
                            alreadyChecked.clear();
                        }
                );
            }
        }
    }

    private void generateLeaveaAround(Chunk chunk, Random random, BlockPos pos) {
        int lvl = 2+random.nextInt(3);
        spreadOut(chunk, pos, enderLeaves, AIR, lvl);
        spreadOut(chunk, pos, enderLeaves, AIR, lvl - 1);
        spreadOut(chunk, pos, enderLeaves, AIR, lvl - 2);
    }

    private EnumFacing logDirection(Chunk chunk, BlockPos pos) {
        return
                Arrays.stream(EnumFacing.values())
                        .filter((i -> chunk.getBlockState(pos.offset(i)).getBlock() == AIR))
                        .findAny()
                        .orElse(EnumFacing.UP);
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

    public static void generateInArea(BlockPos start, BlockPos end, Consumer<BlockPos> generate) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int z = start.getZ(); z <= end.getZ(); z++) {
                for (int y = start.getY(); y <= end.getY(); y++) {
                    pos.setPos(x, y, z);
                    generate.accept(pos);
                }
            }
        }
    }

    public static void spreadOut(Chunk chunk, BlockPos startPos, IBlockState block, Block air, int lvl, double chance) {
        HashSet<BlockPos> alreadyChecked = new HashSet<>();
        List<BlockPos> setLeaves = new ArrayList<>();
        generateInArea(
                new BlockPos(startPos.getX() - lvl, startPos.getY() - lvl, startPos.getZ() - lvl),
                new BlockPos(startPos.getX() + lvl, startPos.getY() + lvl, startPos.getZ() + lvl),
                pos -> {
                    if (/*chunkContains(chunk, pos) && */pos.distanceSq(startPos) < lvl * lvl) {
                        if (!chunk.getWorld().isAirBlock(pos) && aroundBlocks(chunk, pos, air, 1, alreadyChecked)) {
                            //chunk.getWorld().setBlockState(pos, REDSTONE_BLOCK.getDefaultState());
                            Arrays.stream(EnumFacing.values())
                                    .map(pos::offset)
                                    //.filter(i -> chunkContains(chunk, i))
                                    .filter(i -> chunk.getWorld().getBlockState(i).getBlock() == air)
                                    /*.filter(i -> {
                                        double t = TREE_NOISE.getValue(i.getX() * 10, i.getZ() * 10);
                                        return t < 0;
                                    })*/
                                    .forEach(setLeaves::add);
                        }
                        alreadyChecked.clear();
                    }
                }
        );
        setLeaves.forEach(i -> chunk.getWorld().setBlockState(i, block));
    }

    private static int dotProduct(Vec3i a, Vec3i b) {
        return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
    }

    private static boolean chunkContains(Chunk chunk, BlockPos pos) {
        return chunk.getPos().getXStart() <= pos.getX() && chunk.getPos().getXEnd() >= pos.getX() &&
                chunk.getPos().getZStart() <= pos.getZ() && chunk.getPos().getZEnd() >= pos.getZ();
    }


    public static Stream<BlockPos> walkAround(World world, BlockPos current, EnumFacing dirrection, EnumFacing up, int step) {
        return Stream.iterate(new TraverseState(current, dirrection, up, 0),
                state -> nextTraverseState(world, state)).map(TraverseState::getCurrent);
    }

    private static TraverseState nextTraverseState(World world, TraverseState state) {
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
