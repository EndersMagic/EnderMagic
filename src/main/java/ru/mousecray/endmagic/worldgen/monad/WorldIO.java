package ru.mousecray.endmagic.worldgen.monad;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.function.Function;

public interface WorldIO<E extends OnlyWorld, T> {
    T performIO(E world);

    static <T1> WorldIO<OnlyWorld, T1> lift(T1 value) {
        return __ -> value;
    }

    static <E1 extends OnlyWorld> WorldIO<E1, Void> start() {
        return (WorldIO<E1, Void>) none;
    }

    static WorldIO<OnlyWorld, Void> none = __ -> null;

    default <T1> WorldIO<E, T1> flatMap(Function<T, WorldIO<? super E, T1>> f) {
        return (E world) -> f.apply(performIO(world)).performIO(world);
    }

    default <T1> WorldIO<E, T1> flatten() {
        return flatMap(i -> (WorldIO<E, T1>) i);
    }

    default <T1> WorldIO<E, T1> andThen(WorldIO<? super E, T1> second) {
        return flatMap(__ -> second);
    }

    static <E extends OnlyWorld, A1> WorldIO<E, A1> sequence(Collection<WorldIO<E, A1>> seq) {
        return seq.stream().reduce(WorldIO::andThen).orElse((WorldIO<E, A1>) none);
    }


    static WorldIO<OnlyWorld, IBlockState> getBlockState(BlockPos pos) {
        return world -> world.world.getBlockState(pos);
    }

    static WorldIO<OnlyWorld, Void> setBlockState(BlockPos pos, IBlockState state) {
        return world -> {
            world.world.setBlockState(pos, state);
            return null;
        };
    }

    static WorldIO<OnlyWorld, BlockPos> getTopPos(BlockPos pos) {
        return world -> world.world.getTopSolidOrLiquidBlock(pos);
    }

    static WorldIO<OnlyWorld, BlockPos> getTopPos(int x, int z) {
        return world -> world.world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
    }
}
