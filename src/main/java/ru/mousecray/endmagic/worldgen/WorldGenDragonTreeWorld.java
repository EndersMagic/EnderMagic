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
        HashSet<BlockPos> alreadyChecked = new HashSet<>();
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
                            if (chunk.getBlockState(pos).getBlock() == END_STONE && aroundBlocks(chunk, pos, AIR, 4, alreadyChecked)) {
                                if (random.nextInt(100) == 0) {
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
                    }
                }
            }
        }
    }

    private void generateLeaveaAround(Chunk chunk, Random random, BlockPos pos) {
        int lvl = random.nextInt(4);
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


    public static void spreadOut(Chunk chunk, BlockPos startPos, IBlockState block, Block air, int lvl) {
        HashSet<BlockPos> alreadyChecked = new HashSet<>();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = startPos.getX() - lvl; x <= startPos.getX() + lvl; x++) {
            for (int z = startPos.getZ() - lvl; z <= startPos.getZ() + lvl; z++) {
                for (int y = startPos.getY() - lvl; y <= startPos.getY() + lvl; y++) {
                    pos.setPos(x, y, z);
                    if (/*chunkContains(chunk, pos) && */pos.distanceSq(startPos) < lvl * lvl) {

                        if (chunk.getBlockState(pos).getBlock() != air && aroundBlocks(chunk, pos, air, 1, alreadyChecked)) {
                            Arrays.stream(EnumFacing.values())
                                    .map(pos::offset)
                                    //.filter(i -> chunkContains(chunk, i))
                                    .filter(i -> chunk.getWorld().getBlockState(i).getBlock() == air)
                                    .forEach(i -> chunk.getWorld().setBlockState(i, block));
                        }
                        alreadyChecked.clear();

                    }
                }
            }
        }
    }

    private static class IgnoreOneBlockPos extends BlockPos {
        private final char ignoreCoord;

        public IgnoreOneBlockPos(int x, int y, int z, char ignoreCoord) {
            super(x, y, z);
            this.ignoreCoord = ignoreCoord;
        }

        public IgnoreOneBlockPos(BlockPos pos, char ignoreCoord) {
            super(pos);
            this.ignoreCoord = ignoreCoord;
        }

        @Override
        public boolean equals(Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            } else if (!(p_equals_1_ instanceof Vec3i)) {
                return false;
            } else {
                Vec3i vec3i = (Vec3i) p_equals_1_;

                int x1 = getX();
                int y1 = getY();
                int z1 = getZ();

                int x2 = vec3i.getX();
                int y2 = vec3i.getY();
                int z2 = vec3i.getZ();
                switch (ignoreCoord) {
                    case 'x':
                        x1 = x2 = 0;
                    case 'y':
                        y1 = y2 = 0;
                    case 'z':
                        z1 = z2 = 0;
                    default:
                }
                return x1 == x2 && y1 == y2 && z1 == z2;

            }
        }

        @Override
        public int hashCode() {
            int x1 = getX();
            int y1 = getY();
            int z1 = getZ();
            switch (ignoreCoord) {
                case 'x':
                    x1 = 0;
                case 'y':
                    y1 = 0;
                case 'z':
                    z1 = 0;
                default:
            }

            return (y1 + z1 * 31) * 31 + x1;
        }

        @Override
        public IgnoreOneBlockPos offset(EnumFacing facing) {
            return offset(facing, 1);
        }

        @Override
        public IgnoreOneBlockPos offset(EnumFacing facing, int n) {
            return n == 0 ? this : new IgnoreOneBlockPos(getX() + facing.getFrontOffsetX() * n, getY() + facing.getFrontOffsetY() * n, getZ() + facing.getFrontOffsetZ() * n, ignoreCoord);
        }
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
