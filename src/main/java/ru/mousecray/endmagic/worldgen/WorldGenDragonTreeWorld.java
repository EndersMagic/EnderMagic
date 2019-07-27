package ru.mousecray.endmagic.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static net.minecraft.init.Blocks.AIR;
import static net.minecraft.init.Blocks.END_STONE;

public class WorldGenDragonTreeWorld {

    protected static final NoiseGeneratorPerlin TREE_NOISE = new NoiseGeneratorPerlin(new Random(1234L), 1);

    private WorldGenDragonTree generator = new WorldGenDragonTree(true);

    private static IBlockState enderLog = EMBlocks.enderLog.getDefaultState();
    private static IBlockState enderLeaves = EMBlocks.enderLeaves.getDefaultState();

    public static int centralIslandSize = 9;
    //3;minecraft:bedrock,2*minecraft:dirt,minecraft:end_portal;1;

    public void generateWorld(Random random, int chunkX, int chunkZ, World world) {
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
                            if (random.nextInt(40) == 0) {

                                EnumFacing direction = logDirection(world, pos).getOpposite();
                                BlockLog.EnumAxis value = BlockLog.EnumAxis.fromFacingAxis(direction.getAxis());
                                if (value != BlockLog.EnumAxis.Y) {
                                    if (world.getBlockState(pos).getBlock() == END_STONE && aroundBlocks(world, pos, AIR, 4, alreadyChecked)) {

                                        int lvl = 2 + random.nextInt(3);
                                        for (int i = 0; i < lvl; i++)
                                            world.setBlockState(pos.offset(direction, i), enderLog.withProperty(LOG_AXIS, value), 18);

                                        generateLeaveaAround(world, pos.offset(direction), lvl);
                                    }
                                }

                            }
                            alreadyChecked.clear();
                        }
                );
            }
        }
    }

    private static void generateLeaveaAround(World world, BlockPos pos, int lvl) {
        spreadOut(world, pos, enderLeaves, AIR, lvl, 0.3);
        spreadOut(world, pos, enderLeaves, AIR, lvl, 0.3);
        spreadOut(world, pos, enderLeaves, AIR, lvl, 0.3);
    }

    private static EnumFacing logDirection(World world, BlockPos pos) {
        return
                Arrays.stream(EnumFacing.values())
                        .filter((i -> world.getBlockState(pos.offset(i)).getBlock() == AIR))
                        .findAny()
                        .orElse(EnumFacing.UP);
    }

    public static boolean aroundBlocks(World world, BlockPos pos, Block air, int n, HashSet<BlockPos> alreadyChecked) {
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

    public static boolean[][][] spreadOut(World world, BlockPos startPos, IBlockState block, Block air, int lvl, double chance) {
        HashSet<BlockPos> alreadyChecked = new HashSet<>();
        List<Pair<BlockPos, IBlockState>> setLeaves = new ArrayList<>();

        int areaSize = lvl * 2 + 1;
        boolean[][][] marks = new boolean[areaSize][areaSize][areaSize];
        BlockPos start = new BlockPos(startPos.getX() - lvl, startPos.getY() - lvl, startPos.getZ() - lvl);
        generateInArea(
                start,
                new BlockPos(startPos.getX() + lvl, startPos.getY() + lvl, startPos.getZ() + lvl),
                pos -> {
                    if (/*chunkContains(chunk, pos) && */pos.distanceSq(startPos) < lvl * lvl) {
                        if (world.getBlockState(pos).getBlock() == air && aroundBlocks(world, pos, air, 1, alreadyChecked)) {
                            Arrays.stream(EnumFacing.values())
                                    .map(pos::offset)
                                    .filter(i -> world.getBlockState(i).getBlock() == air)
                                    .map(i -> {
                                        if (marks[i.getX() - start.getX()][i.getY() - start.getY()][i.getZ() - start.getZ()]) {
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
