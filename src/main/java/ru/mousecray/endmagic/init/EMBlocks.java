package ru.mousecray.endmagic.init;

import ru.mousecray.endmagic.blocks.BlockBlastFurnace;
import ru.mousecray.endmagic.blocks.BlockNamed;
import ru.mousecray.endmagic.blocks.EnderCrops;
import ru.mousecray.endmagic.blocks.EnderGrass;
import ru.mousecray.endmagic.blocks.EnderOre;
import ru.mousecray.endmagic.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.blocks.portal.BlockPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;

public final class EMBlocks {
    public static final EnderGrass enderGrass = new EnderGrass();

    public static final BlockBlastFurnace blockBlastFurnace = new BlockBlastFurnace();

    public static final BlockMasterDarkPortal blockMasterDarkPortal = new BlockMasterDarkPortal();

    public static final BlockMasterStaticPortal blockMasterStaticPortal = new BlockMasterStaticPortal();

    public static final BlockPortal blockPortal = new BlockPortal();

    public static final BlockTopMark blockTopMark = new BlockTopMark();

    public static final EnderCrops enderCrops = new EnderCrops();

    public static final BlockNamed dragonCoal = new BlockNamed("dragon_coal_block");
    public static final BlockNamed naturalCoal = new BlockNamed("natural_coal_block");
    public static final BlockNamed phantomCoal = new BlockNamed("phantom_coal_block");
    public static final BlockNamed immortalCoal = new BlockNamed("immortal_coal_block");

    public static final BlockNamed technicalEnderit = new BlockNamed("technical_enderit");
    public static final EnderOre enderOre = new EnderOre("ender_ore");
}
