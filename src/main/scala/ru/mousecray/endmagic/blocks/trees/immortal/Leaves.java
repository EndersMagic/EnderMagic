package ru.mousecray.endmagic.blocks.trees.immortal;

import ru.mousecray.endmagic.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.EnderBlockTypes;

public class Leaves extends EMLeaves {
    public Leaves() {
        super(EnderBlockTypes.EnderTreeType.IMMORTAL, () -> EMBlocks.immortalSapling);
    }
}
