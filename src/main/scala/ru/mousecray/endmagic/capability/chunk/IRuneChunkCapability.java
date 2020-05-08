package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.math.BlockPos;
import scala.Option;

import java.util.Map;
import java.util.Optional;

public interface IRuneChunkCapability {

    Optional<RuneState> getRuneState(BlockPos pos);

    RuneState createRuneStateIfAbsent(BlockPos pos);

    void removeRuneState(BlockPos pos);

    Map<BlockPos, RuneState> existingRunes();

}
