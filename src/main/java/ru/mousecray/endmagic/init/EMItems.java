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

public class EMItems {
    public static final Item enderSeeds = new EMSeeds(EMBlocks.enderCrops, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds");
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();

    //public static final ItemTextured simpletexturemodel = ItemTextured.companion.simpletexturemodelItem; //may be unused

    private static final int naturalColor = new Color(0xD29B77).getRGB();
    private static final int phantomColor = new Color(0x86ACC5).getRGB();
    private static final int dragonColor = new Color(0xA87DD2).getRGB();
    private static final int immortalColor = new Color(0xDCBE20).getRGB();

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

    public static final EMShovel naturalSteelShovel = new EMShovel(EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_shovel");
    public static final EMPickaxe naturalSteelPickaxe = new EMPickaxe(EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_pickaxe");
    public static final EMAxe naturalSteelAxe = new EMAxe(EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_axe");
    public static final EMSword naturalSteelSword = new EMSword(EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_sword");
    public static final EMHoe naturalSteelHoe = new EMHoe(EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, "natural_steel_hoe");
    public static final EMArmor naturalSteelHelmet = new EMArmor(EMMaterials.NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "natural_steel_helmet", naturalSteel);
    public static final EMArmor naturalSteelChestplate = new EMArmor(EMMaterials.NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "natural_steel_chestplate", naturalSteel);
    public static final EMArmor naturalSteelLeggings = new EMArmor(EMMaterials.NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "natural_steel_leggings", naturalSteel);
    public static final EMArmor naturalSteelBoots = new EMArmor(EMMaterials.NATURAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "natural_steel_boots", naturalSteel);

    public static final EMShovel phantomSteelShovel = new EMShovel(EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_shovel");
    public static final EMPickaxe phantomSteelPickaxe = new EMPickaxe(EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_pickaxe");
    public static final EMAxe phantomSteelAxe = new EMAxe(EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_axe");
    public static final EMSword phantomSteelSword = new EMSword(EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_sword");
    public static final EMHoe phantomSteelHoe = new EMHoe(EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, "phantom_steel_hoe");
    public static final EMArmor phantomSteelHelmet = new EMArmor(EMMaterials.PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "phantom_steel_helmet", phantomSteel);
    public static final EMArmor phantomSteelChestplate = new EMArmor(EMMaterials.PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "phantom_steel_chestplate", phantomSteel);
    public static final EMArmor phantomSteelLeggings = new EMArmor(EMMaterials.PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "phantom_steel_leggings", phantomSteel);
    public static final EMArmor phantomSteelBoots = new EMArmor(EMMaterials.PHANTOM_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "phantom_steel_boots", phantomSteel);

    public static final EMShovel dragonSteelShovel = new EMShovel(EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_shovel");
    public static final EMPickaxe dragonSteelPickaxe = new EMPickaxe(EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_pickaxe");
    public static final EMAxe dragonSteelAxe = new EMAxe(EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_axe");
    public static final EMSword dragonSteelSword = new EMSword(EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_sword");
    public static final EMHoe dragonSteelHoe = new EMHoe(EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, "dragon_steel_hoe");
    public static final EMArmor dragonSteelHelmet = new EMArmor(EMMaterials.DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "dragon_steel_helmet", dragonSteel);
    public static final EMArmor dragonSteelChestplate = new EMArmor(EMMaterials.DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "dragon_steel_chestplate", dragonSteel);
    public static final EMArmor dragonSteelLeggings = new EMArmor(EMMaterials.DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "dragon_steel_leggings", dragonSteel);
    public static final EMArmor dragonSteelBoots = new EMArmor(EMMaterials.DRAGON_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "dragon_steel_boots", dragonSteel);

    public static final EMShovel immortalSteelShovel = new EMShovel(EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_shovel");
    public static final EMPickaxe immortalSteelPickaxe = new EMPickaxe(EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_pickaxe");
    public static final EMAxe immortalSteelAxe = new EMAxe(EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_axe");
    public static final EMSword immortalSteelSword = new EMSword(EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_sword");
    public static final EMHoe immortalSteelHoe = new EMHoe(EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, "immortal_steel_hoe");
    public static final EMArmor immortalSteelHelmet = new EMArmor(EMMaterials.IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.HEAD, "immortal_steel_helmet", immortalSteel);
    public static final EMArmor immortalSteelChestplate = new EMArmor(EMMaterials.IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.CHEST, "immortal_steel_chestplate", immortalSteel);
    public static final EMArmor immortalSteelLeggings = new EMArmor(EMMaterials.IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.LEGS, "immortal_steel_leggings", immortalSteel);
    public static final EMArmor immortalSteelBoots = new EMArmor(EMMaterials.IMMORTAL_STEEL_ARMOR_MATERIAL, 4, EntityEquipmentSlot.FEET, "immortal_steel_boots", immortalSteel);


    public static final EMShovel naturalDiamondShovel = new EMShovel(EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_shovel");
    public static final EMPickaxe naturalDiamondPickaxe = new EMPickaxe(EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_pickaxe");
    public static final EMAxe naturalDiamondAxe = new EMAxe(EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_axe");
    public static final EMSword naturalDiamondSword = new EMSword(EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_sword");
    public static final EMHoe naturalDiamondHoe = new EMHoe(EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL, "natural_diamond_hoe");

    public static final EMShovel phantomDiamondShovel = new EMShovel(EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_shovel");
    public static final EMPickaxe phantomDiamondPickaxe = new EMPickaxe(EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_pickaxe");
    public static final EMAxe phantomDiamondAxe = new EMAxe(EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_axe");
    public static final EMSword phantomDiamondSword = new EMSword(EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_sword");
    public static final EMHoe phantomDiamondHoe = new EMHoe(EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL, "phantom_diamond_hoe");

    public static final EMShovel dragonDiamondShovel = new EMShovel(EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_shovel");
    public static final EMPickaxe dragonDiamondPickaxe = new EMPickaxe(EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_pickaxe");
    public static final EMAxe dragonDiamondAxe = new EMAxe(EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_axe");
    public static final EMSword dragonDiamondSword = new EMSword(EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_sword");
    public static final EMHoe dragonDiamondHoe = new EMHoe(EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL, "dragon_diamond_hoe");

    public static final EMShovel immortalDiamondShovel = new EMShovel(EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_shovel");
    public static final EMPickaxe immortalDiamondPickaxe = new EMPickaxe(EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_pickaxe");
    public static final EMAxe immortalDiamondAxe = new EMAxe(EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_axe");
    public static final EMSword immortalDiamondSword = new EMSword(EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_sword");
    public static final EMHoe immortalDiamondHoe = new EMHoe(EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL, "immortal_diamond_hoe");

    public static final ItemNamed rawEnderite = new ItemNamed("raw_enderite");

    public static final Item purpleEnderPearl = new PurpleEnderPearl();
    public static final Item blueEnderPearl = new BlueEnderPearl();
    public static final Item enderArrow = new EnderArrow();
    public static final Item enderApple = new EnderApple();
    public static final Item emBook = new EMBook();
}
