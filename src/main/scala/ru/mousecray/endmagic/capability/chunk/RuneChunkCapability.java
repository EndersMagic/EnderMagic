package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class RuneChunkCapability {

    //todo: optimise by positions limit: may encode to long key
    private Map<BlockPos, RuneState> states = new HashMap<>();

    public RuneState getRuneState(BlockPos pos) {
        return states.computeIfAbsent(pos, __ -> new RuneState());
    }
}
