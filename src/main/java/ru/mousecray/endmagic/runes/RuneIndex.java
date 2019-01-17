package ru.mousecray.endmagic.runes;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.util.math.BlockPos;
import scala.Option;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Map;

public class RuneIndex {
    private static Map<Block, Map<BlockPos, RuneState>> index =
            new HashMap<Block, Map<BlockPos, RuneState>>().withDefaultValue(new HashMap<>());

    public static Option<RuneState> getRuneAt(BlockPos pos, Block block) {
        return index.apply(block).get(pos);
    }

    public static void setRuneAt(BlockPos pos, Block block, RuneState state) {
        index.apply(block).put(pos, state);
    }
}
