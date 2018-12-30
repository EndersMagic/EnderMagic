package ru.mousecray.endmagic.init;

import net.minecraft.block.Block;
import ru.mousecray.endmagic.blocks.*;
import ru.mousecray.endmagic.blocks.portal.BlockPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.blocks.portal.MaterBlockPortal;

public final class EMBlocks {
    public static final Block ENDER_GRASS = new EnderGrass();

    public static final Block PORTAL = new BlockPortal();

    public static final Block materBlockPortal = new MaterBlockPortal();

    public static final Block blockTopMark = new BlockTopMark();
}
