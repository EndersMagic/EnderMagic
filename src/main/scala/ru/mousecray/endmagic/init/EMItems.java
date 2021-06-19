package ru.mousecray.endmagic.init;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.items.*;
import ru.mousecray.endmagic.items.inscribers.BaseInscriber;
import ru.mousecray.endmagic.items.materials.EnderCoal;
import ru.mousecray.endmagic.items.materials.EnderDiamond;
import ru.mousecray.endmagic.items.materials.EnderSteel;
import ru.mousecray.endmagic.items.materials.MaterialProvider;
import ru.mousecray.endmagic.items.tools.*;
import ru.mousecray.endmagic.rune.RuneColor;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.HSBA;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EMItems {
    public static final ItemNamed rawEnderite = new ItemNamed("raw_enderite");
    public static final Item enderCompass = new EnderCompass();
    public static final RuneShaper runeShaper = new RuneShaper();
    public static final RuneEraser runeEraser = new RuneEraser();
    public static final RuneDeployer runeDeployer = new RuneDeployer();
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();
    public static final Item purpleEnderPearl = new PurpleEnderPearl();
    public static final Item blueEnderPearl = new BlueEnderPearl();
    public static final Item enderArrow = new EnderArrow();
    public static final Item enderApple = new EnderApple();
    public static final Item emBook = new EMBook();
    public static final Item test = new Test();
    public static final ShiningQuartz shiningQuartz = new ShiningQuartz();
    
    public static final ItemNamed conductive_core = new ItemNamed("conductive_core");
    //TODO: add custom end grass and remove STONE from this
    public static final Item enderSeeds = new EMSeeds(() -> EMBlocks.enderCrops, "ender_seeds", "tooltip.ender_seeds", EndSoilType.STONE, EndSoilType.GRASS);
    //@formatter:off
    //public static final ItemTextured simpletexturemodel = ItemTextured.companion.simpletexturemodelItem; //may be unused
    private static final int naturalColor = new Color(0xEAB277).getRGB();
    public static final ItemNamed naturalCoal = new EnderCoal("natural", naturalColor);
    public static final ItemNamed naturalSteel = new EnderSteel("natural", naturalColor, EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, EMMaterials.NATURAL_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed naturalDiamond = new EnderDiamond("natural", naturalColor, EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL);
    private static final int phantomColor = new Color(0xFF25B8CB,true).getRGB();
    public static final ItemNamed phantomCoal = new EnderCoal("phantom", phantomColor);
    public static final ItemNamed phantomSteel = new EnderSteel("phantom", phantomColor, EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, EMMaterials.PHANTOM_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed phantomDiamond = new EnderDiamond("phantom", phantomColor, EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL);
    private static final int dragonColor = new Color(0xA87DD2).getRGB();
    public static final ItemNamed dragonCoal = new EnderCoal("dragon", dragonColor);
    public static final ItemNamed dragonSteel = new EnderSteel("dragon", dragonColor, EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, EMMaterials.DRAGON_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed dragonDiamond = new EnderDiamond("dragon", dragonColor, EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL);
    private static final int immortalColor = new Color(0xE5D67E).getRGB();
    public static final ItemNamed immortalCoal = new EnderCoal("immortal", immortalColor);
    public static final ItemNamed immortalSteel = new EnderSteel("immortal", immortalColor, EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, EMMaterials.IMMORTAL_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed immortalDiamond = new EnderDiamond("immortal", immortalColor, EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL);
    private static final List<Item> steelToolsAndArmor;
    private static final List<Item> diamondTools;
    private static final List<Item> inscribers;
    //formatter:on

    static {
        steelToolsAndArmor = Stream.of(naturalSteel, phantomSteel, dragonSteel, immortalSteel)
                .flatMap(item ->
                        {
                            MaterialProvider material = (MaterialProvider) item;
                            int materialColor = material.color();
                            HSBA hsba = RGBA.fromARGB(materialColor).toHSBA();
                            int toolColor = hsba.setB(0.7f).toRGBA().argb();
                            return Stream.of(
                                    new EMShovel(material.material(), item.getCustomName() + "_shovel", toolColor),
                                    new EMPickaxe(material.material(), item.getCustomName() + "_pickaxe", toolColor),
                                    new EMAxe(material.material(), item.getCustomName() + "_axe", toolColor),
                                    new EMSword(material.material(), item.getCustomName() + "_sword", toolColor),
                                    new EMHoe(material.material(), item.getCustomName() + "_hoe", toolColor),
                                    new EMArmor(material.armorMaterial(), 4, EntityEquipmentSlot.HEAD, item.getCustomName() + "_helmet", item, toolColor),
                                    new EMArmor(material.armorMaterial(), 4, EntityEquipmentSlot.CHEST, item.getCustomName() + "_chestplate", item, toolColor),
                                    new EMArmor(material.armorMaterial(), 4, EntityEquipmentSlot.LEGS, item.getCustomName() + "_leggings", item, toolColor),
                                    new EMArmor(material.armorMaterial(), 4, EntityEquipmentSlot.FEET, item.getCustomName() + "_boots", item, toolColor)
                            );
                        }
                ).collect(Collectors.toList());

        diamondTools = Stream.of(naturalDiamond, phantomDiamond, dragonDiamond, immortalDiamond)
                .flatMap(item ->
                        {
                            MaterialProvider material = (MaterialProvider) item;
                            int materialColor = material.color();
                            HSBA hsba = RGBA.fromARGB(materialColor).toHSBA();
                            int toolColor = hsba.setS(hsba.getS() + 0.3f).setB(1).toRGBA().argb();
                            return Stream.of(
                                    new EMShovel(material.material(), item.getCustomName() + "_shovel", toolColor),
                                    new EMPickaxe(material.material(), item.getCustomName() + "_pickaxe", toolColor),
                                    new EMAxe(material.material(), item.getCustomName() + "_axe", toolColor),
                                    new EMSword(material.material(), item.getCustomName() + "_sword", toolColor),
                                    new EMHoe(material.material(), item.getCustomName() + "_hoe", toolColor)
                            );
                        }
                ).collect(Collectors.toList());

        inscribers= Arrays.stream(RuneColor.values())
                .map(runeColor -> new BaseInscriber(runeColor, 0.7))
                .collect(Collectors.toList());
    }

    public static List<Item> steelToolsAndArmor() {
        return steelToolsAndArmor;
    }

    public static List<Item> diamondTools() {
        return diamondTools;
    }

    public static List<Item> inscribers() {
        return inscribers;
    }
}