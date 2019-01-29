package ru.mousecray.endmagic.runes;

import net.minecraft.block.Block;
import ru.mousecray.endmagic.teleport.Location;
import scala.Option;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Map;

public class RuneIndex {
    private static Map<Block, Map<Location, RuneState>> index =
            new HashMap<Block, Map<Location, RuneState>>().withDefaultValue(new HashMap<>());

    public static Option<RuneState> getRuneAt(Location pos, Block block) {
        return index.apply(block).get(pos);
    }

    public static void setRuneAt(Location pos, Block block, RuneState state) {
        index.apply(block).put(pos, state);
    }
}
