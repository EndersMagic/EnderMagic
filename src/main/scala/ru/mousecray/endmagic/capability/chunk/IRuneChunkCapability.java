package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;

import java.util.Map;

public interface IRuneChunkCapability {

    RuneState getRuneState(BlockPos pos);

    void setRuneState(BlockPos pos, RuneState state);

    default void removeRuneState(BlockPos pos) {
        setRuneState(pos, RuneState.empty);
    }

    Map<BlockPos, RuneState> existingRunes();

}
