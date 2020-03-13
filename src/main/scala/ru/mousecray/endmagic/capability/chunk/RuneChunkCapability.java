package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.EM;

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
        return states.computeIfAbsent(pos,RuneState::new);
    }

    public void removeRuneStateAt(BlockPos pos) {
        if (states.containsKey(pos)) {
            states.remove(pos);
            EM.proxy.refreshChunk(pos);
        }
    }
}
