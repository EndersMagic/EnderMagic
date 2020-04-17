package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.EM;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonRuneChunkCapability implements IRuneChunkCapability {

    //todo: optimise by positions limit: may encode to long key
    public Map<BlockPos, RuneState> states = new HashMap<>();

    public RuneState getRuneState(BlockPos pos) {
        return states.getOrDefault(pos, RuneState.empty());
    }

    public void setRuneState(BlockPos pos, RuneState state) {
        states.put(pos, state);
    }

    public void removeRuneState(BlockPos pos) {
            states.remove(pos);
    }

    @Override
    public Map<BlockPos, RuneState> existingRunes() {
        return states;
    }
}
