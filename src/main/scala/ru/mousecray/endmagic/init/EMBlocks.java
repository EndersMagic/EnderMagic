package ru.mousecray.endmagic.init;

import ru.mousecray.endmagic.blocks.*;
import ru.mousecray.endmagic.blocks.dimensional.EnderGroundGrass;
import ru.mousecray.endmagic.blocks.dimensional.EnderGroundStone;
import ru.mousecray.endmagic.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.blocks.portal.Portal;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

public final class EMBlocks {

    public static final EnderCrops enderCrops = new EnderCrops();
    public static final EnderTallgrass enderTallgrass = new EnderTallgrass();
    public static final EnderOrchid enderOrchid = new EnderOrchid();

    public static final BlockBlastFurnace blockBlastFurnace = new BlockBlastFurnace();

    public static final BlockMasterDarkPortal blockMasterDarkPortal = new BlockMasterDarkPortal();
    public static final BlockMasterStaticPortal blockMasterStaticPortal = new BlockMasterStaticPortal();
    public static final Portal blockPortal = new Portal();
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

    public static final ru.mousecray.endmagic.blocks.trees.dragon.Leaves dragonLeaves = new ru.mousecray.endmagic.blocks.trees.dragon.Leaves();
    public static final ru.mousecray.endmagic.blocks.trees.immortal.Leaves immortalLeaves = new ru.mousecray.endmagic.blocks.trees.immortal.Leaves();
    public static final ru.mousecray.endmagic.blocks.trees.natural.Leaves naturalLeaves = new ru.mousecray.endmagic.blocks.trees.natural.Leaves();
    public static final ru.mousecray.endmagic.blocks.trees.phantom.Leaves phantomLeaves = new ru.mousecray.endmagic.blocks.trees.phantom.Leaves();

    public static final ru.mousecray.endmagic.blocks.trees.dragon.Log dragonLog = new ru.mousecray.endmagic.blocks.trees.dragon.Log();
    public static final ru.mousecray.endmagic.blocks.trees.immortal.Log immortalLog = new ru.mousecray.endmagic.blocks.trees.immortal.Log();
    public static final ru.mousecray.endmagic.blocks.trees.natural.Log naturalLog = new ru.mousecray.endmagic.blocks.trees.natural.Log();
    public static final ru.mousecray.endmagic.blocks.trees.phantom.Log phantomLog = new ru.mousecray.endmagic.blocks.trees.phantom.Log();

    public static final ru.mousecray.endmagic.blocks.trees.dragon.Sapling dragonSapling = new ru.mousecray.endmagic.blocks.trees.dragon.Sapling();
    public static final ru.mousecray.endmagic.blocks.trees.immortal.Sapling immortalSapling = new ru.mousecray.endmagic.blocks.trees.immortal.Sapling();
    public static final ru.mousecray.endmagic.blocks.trees.natural.Sapling naturalSapling = new ru.mousecray.endmagic.blocks.trees.natural.Sapling();
    public static final ru.mousecray.endmagic.blocks.trees.phantom.Sapling phantomSapling = new ru.mousecray.endmagic.blocks.trees.phantom.Sapling();


    public static final ru.mousecray.endmagic.blocks.trees.dragon.Planks dragonPlanks = new ru.mousecray.endmagic.blocks.trees.dragon.Planks();
    public static final ru.mousecray.endmagic.blocks.trees.immortal.Planks immortalPlanks = new ru.mousecray.endmagic.blocks.trees.immortal.Planks();
    public static final ru.mousecray.endmagic.blocks.trees.natural.Planks naturalPlanks = new ru.mousecray.endmagic.blocks.trees.natural.Planks();
    public static final ru.mousecray.endmagic.blocks.trees.phantom.Planks phantomPlanks = new ru.mousecray.endmagic.blocks.trees.phantom.Planks();

    public static final EMSlab.EMSlabDouble enderWoodenSlabDouble = new EMSlab.EMSlabDouble();
    public static final EMSlab.EMSlabSingle enderWoodenSlabSingle = new EMSlab.EMSlabSingle();

    public static final EnderGroundGrass ENDER_GROUND_GRASS = new EnderGroundGrass();
    public static final EnderGroundStone ENDER_GROUND_STONE = new EnderGroundStone();

    public static final ChrysofillumVine chrysVine = new ChrysofillumVine();
    public static final ChrysofillumFlower chrysFlower = new ChrysofillumFlower();
    public static final ChrysofillumFruit chrysFruit = new ChrysofillumFruit();
}