package ru.mousecray.endmagic.init;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.items.*;
import ru.mousecray.endmagic.items.materials.EnderCoal;
import ru.mousecray.endmagic.items.materials.EnderDiamond;
import ru.mousecray.endmagic.items.materials.EnderSteel;
import ru.mousecray.endmagic.items.tools.*;

import java.awt.*;

import static ru.mousecray.endmagic.init.EMMaterials.*;

public class EMItems {
    public static final Item enderSeeds = new EMSeeds(EMBlocks.enderCrops, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds");
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();

    //public static final ItemTextured simpletexturemodel = ItemTextured.companion.simpletexturemodelItem; //may be unused

    private static final int naturalColor = new Color(0xEAB277).getRGB();
    private static final int phantomColor = new Color(0xA9D7F2).getRGB();
    private static final int dragonColor = new Color(0xA87DD2).getRGB();
    private static final int immortalColor = new Color(0xE5D67E).getRGB();

    private static final int naturalColorTool = new Color(0xE89746).getRGB();
    private static final int phantomColorTool = new Color(0xA9D7F2).getRGB();
    private static final int dragonColorTool = new Color(0xA74FFF).getRGB();
    private static final int immortalColorTool = new Color(0xFFE14D).getRGB();

    public static final ItemNamed naturalCoal = new EnderCoal("natural_coal", naturalColor);
    public static final ItemNamed phantomCoal = new EnderCoal("phantom_coal", phantomColor);
    public static final ItemNamed dragonCoal = new EnderCoal("dragon_coal", dragonColor);
    public static final ItemNamed immortalCoal = new EnderCoal("immortal_coal", immortalColor);

    public static final ItemNamed naturalSteel = new EnderSteel("natural_steel", naturalColor);
    public static final ItemNamed phantomSteel = new EnderSteel("phantom_steel", phantomColor);
    public static final ItemNamed dragonSteel = new EnderSteel("dragon_steel", dragonColor);
    public static final ItemNamed immortalSteel = new EnderSteel("immortal_steel", immortalColor);

    public static final ItemNamed naturalDiamond = new EnderDiamond("natural_diamond", naturalColor);
    public static final ItemNamed phantomDiamond = new EnderDiamond("phantom_diamond", phantomColor);
    public static final ItemNamed dragonDiamond = new EnderDiamond("dragon_diamond", dragonColor);
    public static final ItemNamed immortalDiamond = new EnderDiamond("immortal_diamond", immortalColor);

    public static final EMShovel naturalSteelShovel = new EMShovel(NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_shovel", naturalColorTool);
    public static final EMPickaxe naturalSteelPickaxe = new EMPickaxe(NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_pickaxe", naturalColorTool);
    public static final EMAxe naturalSteelAxe = new EMAxe(NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_axe", naturalColorTool);
    public static final EMSword naturalSteelSword = new EMSword(NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_sword", naturalColorTool);
    public static final EMHoe naturalSteelHoe = new EMHoe(NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_hoe", naturalColorTool);
    public static final EMArmor naturalSteelHelmet = new EMArmor(NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "natural_steel_helmet", naturalSteel, naturalColorTool);
    public static final EMArmor naturalSteelChestplate = new EMArmor(NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "natural_steel_chestplate", naturalSteel, naturalColorTool);
    public static final EMArmor naturalSteelLeggings = new EMArmor(NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "natural_steel_leggings", naturalSteel, naturalColorTool);
    public static final EMArmor naturalSteelBoots = new EMArmor(NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "natural_steel_boots", naturalSteel, naturalColorTool);

    public static final EMShovel phantomSteelShovel = new EMShovel(PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_shovel", phantomColorTool);
    public static final EMPickaxe phantomSteelPickaxe = new EMPickaxe(PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_pickaxe", phantomColorTool);
    public static final EMAxe phantomSteelAxe = new EMAxe(PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_axe", phantomColorTool);
    public static final EMSword phantomSteelSword = new EMSword(PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_sword", phantomColorTool);
    public static final EMHoe phantomSteelHoe = new EMHoe(PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_hoe", phantomColorTool);
    public static final EMArmor phantomSteelHelmet = new EMArmor(PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "phantom_steel_helmet", phantomSteel, phantomColorTool);
    public static final EMArmor phantomSteelChestplate = new EMArmor(PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "phantom_steel_chestplate", phantomSteel, phantomColorTool);
    public static final EMArmor phantomSteelLeggings = new EMArmor(PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "phantom_steel_leggings", phantomSteel, phantomColorTool);
    public static final EMArmor phantomSteelBoots = new EMArmor(PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "phantom_steel_boots", phantomSteel, phantomColorTool);

    public static final EMShovel dragonSteelShovel = new EMShovel(DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_shovel", dragonColorTool);
    public static final EMPickaxe dragonSteelPickaxe = new EMPickaxe(DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_pickaxe", dragonColorTool);
    public static final EMAxe dragonSteelAxe = new EMAxe(DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_axe", dragonColorTool);
    public static final EMSword dragonSteelSword = new EMSword(DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_sword", dragonColorTool);
    public static final EMHoe dragonSteelHoe = new EMHoe(DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_hoe", dragonColorTool);
    public static final EMArmor dragonSteelHelmet = new EMArmor(DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "dragon_steel_helmet", dragonSteel, dragonColorTool);
    public static final EMArmor dragonSteelChestplate = new EMArmor(DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "dragon_steel_chestplate", dragonSteel, dragonColorTool);
    public static final EMArmor dragonSteelLeggings = new EMArmor(DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "dragon_steel_leggings", dragonSteel, dragonColorTool);
    public static final EMArmor dragonSteelBoots = new EMArmor(DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "dragon_steel_boots", dragonSteel, dragonColorTool);

    public static final EMShovel immortalSteelShovel = new EMShovel(IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_shovel", immortalColorTool);
    public static final EMPickaxe immortalSteelPickaxe = new EMPickaxe(IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_pickaxe", immortalColorTool);
    public static final EMAxe immortalSteelAxe = new EMAxe(IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_axe", immortalColorTool);
    public static final EMSword immortalSteelSword = new EMSword(IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_sword", immortalColorTool);
    public static final EMHoe immortalSteelHoe = new EMHoe(IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_hoe", immortalColorTool);
    public static final EMArmor immortalSteelHelmet = new EMArmor(IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "immortal_steel_helmet", immortalSteel, immortalColorTool);
    public static final EMArmor immortalSteelChestplate = new EMArmor(IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "immortal_steel_chestplate", immortalSteel, immortalColorTool);
    public static final EMArmor immortalSteelLeggings = new EMArmor(IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "immortal_steel_leggings", immortalSteel, immortalColorTool);
    public static final EMArmor immortalSteelBoots = new EMArmor(IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "immortal_steel_boots", immortalSteel, immortalColorTool);


    public static final EMShovel naturalDiamondShovel = new EMShovel(NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_shovel", naturalColorTool);
    public static final EMPickaxe naturalDiamondPickaxe = new EMPickaxe(NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_pickaxe", naturalColorTool);
    public static final EMAxe naturalDiamondAxe = new EMAxe(NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_axe", naturalColorTool);
    public static final EMSword naturalDiamondSword = new EMSword(NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_sword", naturalColorTool);
    public static final EMHoe naturalDiamondHoe = new EMHoe(NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_hoe", naturalColorTool);

    public static final EMShovel phantomDiamondShovel = new EMShovel(PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_shovel", phantomColorTool);
    public static final EMPickaxe phantomDiamondPickaxe = new EMPickaxe(PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_pickaxe", phantomColorTool);
    public static final EMAxe phantomDiamondAxe = new EMAxe(PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_axe", phantomColorTool);
    public static final EMSword phantomDiamondSword = new EMSword(PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_sword", phantomColorTool);
    public static final EMHoe phantomDiamondHoe = new EMHoe(PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_hoe", phantomColorTool);

    public static final EMShovel dragonDiamondShovel = new EMShovel(DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_shovel", dragonColorTool);
    public static final EMPickaxe dragonDiamondPickaxe = new EMPickaxe(DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_pickaxe", dragonColorTool);
    public static final EMAxe dragonDiamondAxe = new EMAxe(DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_axe", dragonColorTool);
    public static final EMSword dragonDiamondSword = new EMSword(DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_sword", dragonColorTool);
    public static final EMHoe dragonDiamondHoe = new EMHoe(DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_hoe", dragonColorTool);

    public static final EMShovel immortalDiamondShovel = new EMShovel(IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_shovel", immortalColorTool);
    public static final EMPickaxe immortalDiamondPickaxe = new EMPickaxe(IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_pickaxe", immortalColorTool);
    public static final EMAxe immortalDiamondAxe = new EMAxe(IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_axe", immortalColorTool);
    public static final EMSword immortalDiamondSword = new EMSword(IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_sword", immortalColorTool);
    public static final EMHoe immortalDiamondHoe = new EMHoe(IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_hoe", immortalColorTool);

    public static final ItemNamed rawEnderite = new ItemNamed("raw_enderite");

    public static final Item purpleEnderPearl = new PurpleEnderPearl();
    public static final Item blueEnderPearl = new BlueEnderPearl();
    public static final Item enderArrow = new EnderArrow();
    public static final Item enderApple = new EnderApple();
    public static final Item emBook = new EMBook();
}
