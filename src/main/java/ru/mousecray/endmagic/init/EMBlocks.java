package ru.mousecray.endmagic.init;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import ru.mousecray.endmagic.blocks.*;
import ru.mousecray.endmagic.blocks.dimensional.BlockEnderGrass;
import ru.mousecray.endmagic.blocks.dimensional.BlockEnderStone;
import ru.mousecray.endmagic.blocks.portal.BlockMasterDarkPortal;
import ru.mousecray.endmagic.blocks.portal.BlockMasterStaticPortal;
import ru.mousecray.endmagic.blocks.portal.BlockPortal;
import ru.mousecray.endmagic.blocks.portal.BlockTopMark;
import ru.mousecray.endmagic.blocks.trees.EMLeaves;
import ru.mousecray.endmagic.blocks.trees.EMLog;
import ru.mousecray.endmagic.blocks.trees.EMPlanks;
import ru.mousecray.endmagic.blocks.trees.EMSapling;
import ru.mousecray.endmagic.blocks.vanilla.BlockVanillaEndstone;
import ru.mousecray.endmagic.init.util.ClassFieldSource;
import ru.mousecray.endmagic.util.EnderBlockTypes.EnderGroundType;
import ru.mousecray.endmagic.util.EnderBlockTypes.EnderTreeType;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

public final class EMBlocks {

    @ClassFieldSource.SkipRegistry
    public static final BlockVanillaEndstone END_STONE = new BlockVanillaEndstone();

    public static final EnderCrops enderCrops = new EnderCrops();
    public static final EnderTallgrass enderTallgrass = new EnderTallgrass();
    public static final EnderOrchid enderOrchid = new EnderOrchid();

    public static final BlockBlastFurnace blockBlastFurnace = new BlockBlastFurnace();

    public static final BlockMasterDarkPortal blockMasterDarkPortal = new BlockMasterDarkPortal();
    public static final BlockMasterStaticPortal blockMasterStaticPortal = new BlockMasterStaticPortal();
    public static final BlockPortal blockPortal = new BlockPortal();
    public static final BlockTopMark blockTopMark = new BlockTopMark();

    public static final BlockCurseBush blockCurseBush = new BlockCurseBush();

    public static final BlockEnderCoal dragonCoal = new BlockEnderCoal("dragon_coal_block", RGBA.fromRGB(0xff00ff));
    public static final BlockEnderCoal naturalCoal = new BlockEnderCoal("natural_coal_block", RGBA.fromRGB(0xffaa00));
    public static final BlockEnderCoal phantomCoal = new BlockEnderCoal("phantom_coal_block", RGBA.fromRGBA(0x0000ffaa));
    public static final BlockEnderCoal immortalCoal = new BlockEnderCoal("immortal_coal_block", RGBA.fromRGB(0xffff00));

    public static final BlockNamed enderite = new BlockNamed("enderite");
    public static final EnderOre enderOre = new EnderOre("ender_ore");

    public static final EMLog enderLog = new EMLog(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor());
    public static final EMSapling enderSapling = new EMSapling(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor(), type -> ((EnderTreeType) type).getGenerator());
    public static final EMLeaves enderLeaves = new EMLeaves(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor());
    public static final EMPlanks enderPlanks = new EMPlanks(EnderTreeType.class, type -> ((EnderTreeType) type).getMapColor());

    public static final EMSlab enderWoodenSlab = new EMSlab(EnderTreeType.class, Material.WOOD, type -> ((EnderTreeType) type).getMapColor()).setSoundType(SoundType.WOOD).setHardness(2.0F).setResistance(5.0F);

    public static final BlockEnderGrass<EnderGroundType> blockEnderGrass = new BlockEnderGrass<>(EnderGroundType.class, type -> type.getMapColor(), type -> type.getSound());
    public static final BlockEnderStone<EnderGroundType> blockEnderStone = new BlockEnderStone<>(EnderGroundType.class, type -> type.getMapColor());

    public static final ChrysofillumVine chrysVine = new ChrysofillumVine();
    public static final ChrysofillumFlower chrysFlower = new ChrysofillumFlower();
    public static final ChrysofillumFruit chrysFruit = new ChrysofillumFruit();
}