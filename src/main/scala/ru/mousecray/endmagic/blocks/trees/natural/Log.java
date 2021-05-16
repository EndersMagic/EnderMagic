package ru.mousecray.endmagic.blocks.trees.natural;

import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class Log extends EMLog {
    public Log() {
        super(EnderBlockTypes.EnderTreeType.NATURAL);
        setHarvestLevel("axe", 2, getDefaultState());
    }
}
