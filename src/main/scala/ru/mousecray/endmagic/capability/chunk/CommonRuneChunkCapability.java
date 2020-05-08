package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.EM;
import scala.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonRuneChunkCapability implements IRuneChunkCapability {

    //todo: optimise by positions limit: may encode to long key
    public Map<BlockPos, RuneState> states = new HashMap<>();

    public Optional<RuneState> getRuneState(BlockPos pos) {
        return Optional.ofNullable(states.get(pos));
    }

    public RuneState createRuneStateIfAbsent(BlockPos pos) {
        return states.computeIfAbsent(pos, __ -> new RuneState());
    }

    public void removeRuneState(BlockPos pos) {
        states.remove(pos);
    }

    @Override
    public Map<BlockPos, RuneState> existingRunes() {
        return states;
    }
}
