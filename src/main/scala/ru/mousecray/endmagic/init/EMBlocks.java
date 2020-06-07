package ru.mousecray.endmagic.init;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.gameobj.blocks.*;
import ru.mousecray.endmagic.gameobj.blocks.dimensional.BlockEnderGrass;
import ru.mousecray.endmagic.gameobj.blocks.dimensional.BlockEnderStone;
import ru.mousecray.endmagic.gameobj.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.gameobj.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.gameobj.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.gameobj.blocks.portal.Portal;
import ru.mousecray.endmagic.gameobj.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.gameobj.blocks.trees.EMLog;
import ru.mousecray.endmagic.gameobj.blocks.trees.EMPlanks;
import ru.mousecray.endmagic.gameobj.blocks.trees.EMSapling;
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

    public static final EMLog enderLog = new EMLog();
    public static final EMSapling enderSapling = new EMSapling();
    public static final EMLeaves enderLeaves = new EMLeaves();
    public static final EMPlanks enderPlanks = new EMPlanks();

    public static final EMSlab enderWoodenSlab = new EMSlab(Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0F).setResistance(5.0F);

    public static final BlockEnderGrass blockEnderGrass = new BlockEnderGrass(EnderGroundType.class, EnderGroundType::getSound);
    public static final BlockEnderStone blockEnderStone = new BlockEnderStone<>(EnderGroundType.class);

    public static final ChrysofillumVine chrysVine = new ChrysofillumVine();
    public static final ChrysofillumFlower chrysFlower = new ChrysofillumFlower();
    public static final ChrysofillumFruit chrysFruit = new ChrysofillumFruit();
}