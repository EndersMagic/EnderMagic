package ru.mousecray.endmagic.init;

import ru.mousecray.endmagic.blocks.*;
import ru.mousecray.endmagic.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.blocks.portal.BlockPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.blocks.trees.EMSapling;
import ru.mousecray.endmagic.blocks.trees.EnderTreeType;
import ru.mousecray.endmagic.util.elix_x.ecomms.color.RGBA;

public final class EMBlocks {

    public static final EnderGrass enderGrass = new EnderGrass();

    public static final BlockBlastFurnace blockBlastFurnace = new BlockBlastFurnace();

    public static final BlockMasterDarkPortal blockMasterDarkPortal = new BlockMasterDarkPortal();

    public static final BlockMasterStaticPortal blockMasterStaticPortal = new BlockMasterStaticPortal();

    public static final BlockPortal blockPortal = new BlockPortal();

    public static final BlockTopMark blockTopMark = new BlockTopMark();

    public static final EnderCrops enderCrops = new EnderCrops();
    public static final BlockCurseBush blockCurseBush = new BlockCurseBush();

    public static final BlockEnderCoal dragonCoal = new BlockEnderCoal("dragon_coal_block", RGBA.fromRGB(0xff00ff));
    public static final BlockEnderCoal naturalCoal = new BlockEnderCoal("natural_coal_block", RGBA.fromRGB(0xffaa00));
    public static final BlockEnderCoal phantomCoal = new BlockEnderCoal("phantom_coal_block", RGBA.fromRGBA(0x0000ffaa));
    public static final BlockEnderCoal immortalCoal = new BlockEnderCoal("immortal_coal_block", RGBA.fromRGB(0xffff00));

    public static final BlockNamed enderite = new BlockNamed("enderite");
    public static final EnderOre enderOre = new EnderOre("ender_ore");

    public static final EMLog<EnderTreeType> enderLog = new EMLog<>(EnderTreeType.class);
    public static final EMSapling<EnderTreeType> enderSapling = new EMSapling<>(EnderTreeType.class);
    public static final EMLeaves<EnderTreeType> enderLeaves = new EMLeaves<>(EnderTreeType.class);
}
