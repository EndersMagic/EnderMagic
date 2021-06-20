package ru.mousecray.endmagic.init;

import ru.mousecray.endmagic.blocks.*;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.BlockPolishedObsidian;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.BlockPolishedObsidianEndstoneBricks;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.BlockPolishedObsidianEndstoneSlab.BlockPolishedObsidianEndstoneSlabDouble;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.BlockPolishedObsidianEndstoneSlab.BlockPolishedObsidianEndstoneSlabSingle;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.BlockPolishedObsidianSlab.BlockPolishedObsidianSlabDouble;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.BlockPolishedObsidianSlab.BlockPolishedObsidianSlabSingle;
import ru.mousecray.endmagic.blocks.decorative.purpur.quartz.BlockPurpurQuartzBricks;
import ru.mousecray.endmagic.blocks.decorative.purpur.quartz.BlockPurpurQuartzPillar;
import ru.mousecray.endmagic.blocks.decorative.purpur.quartz.BlockPurpurQuartzSlab.BlockPurpurQuartzSlabDouble;
import ru.mousecray.endmagic.blocks.decorative.purpur.quartz.BlockPurpurQuartzSlab.BlockPurpurQuartzSlabSingle;
import ru.mousecray.endmagic.blocks.dimensional.EnderGroundGrass;
import ru.mousecray.endmagic.blocks.dimensional.EnderGroundStone;
import ru.mousecray.endmagic.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.blocks.trees.EMPlanks;
import ru.mousecray.endmagic.blocks.trees.EMSapling;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

public final class EMBlocks {
    public static final BlockPolishedObsidian polishedObsidian = new BlockPolishedObsidian();
    public static final BlockPolishedObsidianSlabDouble polishedObsidianSlabDouble = new BlockPolishedObsidianSlabDouble();
    public static final BlockPolishedObsidianSlabSingle polishedObsidianSlabSingle = new BlockPolishedObsidianSlabSingle();
    public static final BlockPolishedObsidianEndstoneBricks polishedObsidianEndstoneBricks = new BlockPolishedObsidianEndstoneBricks();
    public static final BlockPolishedObsidianEndstoneSlabDouble polishedObsidianEndstoneSlabDouble = new BlockPolishedObsidianEndstoneSlabDouble();
    public static final BlockPolishedObsidianEndstoneSlabSingle polishedObsidianEndstoneSlabSingle = new BlockPolishedObsidianEndstoneSlabSingle();

    public static final BlockPurpurQuartzSlabDouble purpurSlabDouble = new BlockPurpurQuartzSlabDouble();
    public static final BlockPurpurQuartzSlabSingle purpurSlabSingle = new BlockPurpurQuartzSlabSingle();
    public static final BlockPurpurQuartzBricks purpurQuartzBricks = new BlockPurpurQuartzBricks();
    public static final BlockPurpurQuartzPillar purpurQuartzPillar = new BlockPurpurQuartzPillar();

    public static final EnderCrops enderCrops = new EnderCrops();
    public static final EnderTallgrass enderTallgrass = new EnderTallgrass();
    public static final EnderOrchid enderOrchid = new EnderOrchid();

    public static final BlockBlastFurnace blockBlastFurnace = new BlockBlastFurnace();

    public static final BlockMasterDarkPortal blockMasterDarkPortal = new BlockMasterDarkPortal();
    public static final BlockMasterStaticPortal blockMasterStaticPortal = new BlockMasterStaticPortal();
    public static final BlockTopMark blockTopMark = new BlockTopMark();

    public static final BlockCurseBush blockCurseBush = new BlockCurseBush();

    public static final BlockEnderCoal dragonCoal = new BlockEnderCoal("dragon_coal_block", RGBA.fromRGB(0xff00ff));
    public static final BlockEnderCoal naturalCoal = new BlockEnderCoal("natural_coal_block", RGBA.fromRGB(0xffaa00));
    public static final BlockEnderCoal phantomCoal = new BlockEnderCoal("phantom_coal_block", RGBA.fromRGBA(0x0000ffaa));
    public static final BlockEnderCoal immortalCoal = new BlockEnderCoal("immortal_coal_block", RGBA.fromRGB(0xffff00));

    public static final BlockEnderSteel dragonSteel = new BlockEnderSteel("dragon_steel_block", RGBA.fromRGB(0xA87DD2));
    public static final BlockEnderSteel naturalSteel = new BlockEnderSteel("natural_steel_block", RGBA.fromRGB(0xEAB277));
    public static final BlockEnderSteel phantomSteel = new BlockEnderSteel("phantom_steel_block", RGBA.fromRGBA(0xA9D7F2));
    public static final BlockEnderSteel immortalSteel = new BlockEnderSteel("immortal_steel_block", RGBA.fromRGB(0xE5D67E));

    public static final BlockNamed enderite = new BlockNamed("enderite");
    public static final EnderOre enderOre = new EnderOre("ender_ore");

    public static final EMLog enderLog = new EMLog();
    public static final EMSapling enderSapling = new EMSapling();
    public static final EMLeaves enderLeaves = new EMLeaves();
    public static final EMPlanks enderPlanks = new EMPlanks();

    public static final EMSlab.EMSlabDouble enderWoodenSlabDouble = new EMSlab.EMSlabDouble();
    public static final EMSlab.EMSlabSingle enderWoodenSlabSingle = new EMSlab.EMSlabSingle();

    public static final EnderGroundGrass ENDER_GROUND_GRASS = new EnderGroundGrass();
    public static final EnderGroundStone ENDER_GROUND_STONE = new EnderGroundStone();

    public static final ChrysofillumVine chrysVine = new ChrysofillumVine();
    public static final ChrysofillumFlower chrysFlower = new ChrysofillumFlower();
    public static final ChrysofillumFruit chrysFruit = new ChrysofillumFruit();
}