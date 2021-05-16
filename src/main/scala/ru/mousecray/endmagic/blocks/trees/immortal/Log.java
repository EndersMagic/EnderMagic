package ru.mousecray.endmagic.blocks.trees.immortal;

import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class Log extends EMLog {

    public Log() {
        super(EnderBlockTypes.EnderTreeType.IMMORTAL);
        setHarvestLevel("axe", 3, getDefaultState());
        setHardness(3);
    }
}
