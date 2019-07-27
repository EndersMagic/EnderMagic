package ru.mousecray.endmagic.worldgen.monad;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public interface WorldIO<T> {
    T performIO(World world, int chunkX, int chunkZ);

    static <T> WorldIO<T> onlyWorld(Function<World, T> effect) {
        return (world, chunkX, chunkZ) -> effect.apply(world);
    }

    static <T1> WorldIO<T1> lift(T1 value) {
        return (__, ___, ____) -> value;
    }

    static <T1> WorldIO<T1> effect(Supplier<T1> value) {
        return (__, ___, ____) -> value.get();
    }

    static WorldIO<Void> effect(Runnable value) {
        return (__, ___, ____) -> {
            value.run();
            return null;
        };
    }

    static WorldIO<Void> start() {
        return none;
    }

    static WorldIO<Void> none = (__, ___, ____) -> null;

    default <T1> WorldIO<T1> flatMap(Function<T, WorldIO<T1>> f) {
        return (world, chunkX, chunkZ) -> f.apply(performIO(world, chunkX, chunkZ)).performIO(world, chunkX, chunkZ);
    }

    default <T1> WorldIO<T1> map(Function<T, T1> f) {
        return flatMap(f.andThen(WorldIO::lift));
    }

    default <T1> WorldIO<T1> flatten() {
        return flatMap(i -> (WorldIO<T1>) i);
    }

    default <T1> WorldIO<T1> andThen(WorldIO<T1> second) {
        return flatMap(__ -> second);
    }

    default WorldIO<Void> filter(WorldIO<Boolean> condition) {
        return condition.flatMap(c -> c ? andThen(none) : none);
    }

    static WorldIO<Void> whenEffect(WorldIO<Boolean> condition, WorldIO<?> action) {
        return action.filter(condition);
    }

    static <A1> WorldIO<A1> sequence(Collection<WorldIO<A1>> seq) {
        return seq.stream().reduce((WorldIO<A1>) none, WorldIO::andThen);
    }

    static <A1> WorldIO<Void> when(boolean condition, WorldIO<A1> action) {
        if (condition)
            return action.andThen(none);
        else
            return start();
    }


    static WorldIO<IBlockState> getBlockState(BlockPos pos) {
        return (world, __, ___) -> world.getBlockState(pos);
    }

    static WorldIO<Void> setBlockState(BlockPos pos, IBlockState state) {
        return (world, __, ___) -> {
            world.setBlockState(pos, state);
            return null;
        };
    }

    static WorldIO<BlockPos> getTopPos(BlockPos pos) {
        return (world, __, ___) -> world.getTopSolidOrLiquidBlock(pos);
    }

    static WorldIO<BlockPos> getTopPos(int x, int z) {
        return (world, __, ___) -> world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
    }

    static WorldIO<Chunk> getChunkFromChunkCoords = World::getChunkFromChunkCoords;
}
