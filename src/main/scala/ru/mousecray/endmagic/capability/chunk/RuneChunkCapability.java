package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RuneChunkCapability {

    //todo: optimise by positions limit: may encode to long key
    private Map<BlockPos, RuneState> states = new HashMap<>();

    public Optional<RuneState> getRuneState(BlockPos pos) {
        return Optional.ofNullable(states.get(pos));
    }

    public RuneState createRuneStateAt(BlockPos pos) {
        if(states.containsKey(pos))
            return states.get(pos);
        else {
            RuneState value = new RuneState(pos);
            states.put(pos, value);
            return value;
        }
    }
}
