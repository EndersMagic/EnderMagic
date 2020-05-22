package ru.mousecray.endmagic.init;

import ru.mousecray.endmagic.blocks.*;
import ru.mousecray.endmagic.blocks.dimensional.BlockEnderGrass;
import ru.mousecray.endmagic.blocks.dimensional.BlockEnderStone;
import ru.mousecray.endmagic.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.blocks.portal.Portal;
import ru.mousecray.endmagic.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.blocks.trees.EMPlanks;
import ru.mousecray.endmagic.blocks.trees.EMSapling;
import ru.mousecray.endmagic.util.EnderBlockTypes.EnderGroundType;
import ru.mousecray.endmagic.util.EnderBlockTypes.EnderTreeType;
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

    public static final EMLog enderLog = new EMLog(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor());
    public static final EMSapling enderSapling = new EMSapling(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor(),
            type -> ((EnderTreeType) type).getGenerator());
    public static final EMLeaves enderLeaves = new EMLeaves(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor());
    public static final EMPlanks enderPlanks = new EMPlanks(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor());

    public static final EMSlab.EMSlabDouble enderWoodenSlabDouble = new EMSlab.EMSlabDouble();
    public static final EMSlab.EMSlabSingle enderWoodenSlabSingle = new EMSlab.EMSlabSingle();

    public static final BlockEnderGrass<EnderGroundType> blockEnderGrass = new BlockEnderGrass<>(EnderGroundType.class, type -> type.getMapColor(),
            type -> type.getSound());
    public static final BlockEnderStone<EnderGroundType> blockEnderStone = new BlockEnderStone<>(EnderGroundType.class, type -> type.getMapColor());

    public static final ChrysofillumVine chrysVine = new ChrysofillumVine();
    public static final ChrysofillumFlower chrysFlower = new ChrysofillumFlower();
    public static final ChrysofillumFruit chrysFruit = new ChrysofillumFruit();
}