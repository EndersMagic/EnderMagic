package ru.mousecray.endmagic.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import ru.mousecray.endmagic.EM;

public class EMMaterials {

    public static Item.ToolMaterial NATURAL_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_natural_steel", 3, 500, 7F, 2.5F, 12);
    public static Item.ToolMaterial PHANTOM_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_phantom_steel", 3, 500, 7F, 2.5F, 12);
    public static Item.ToolMaterial DRAGON_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_dragon_steel", 3, 500, 7F, 2.5F, 12);
    public static Item.ToolMaterial IMMORTAL_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_immortal_steel", 3, 500, 7F, 2.5F, 12);

    public static Item.ToolMaterial NATURAL_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_natural_diamond", 4, 2000, 10F, 3.5F, 8);
    public static Item.ToolMaterial PHANTOM_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_phantom_diamond", 4, 2000, 10F, 3.5F, 8);
    public static Item.ToolMaterial DRAGON_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_dragon_diamond", 4, 2000, 10F, 3.5F, 8);
    public static Item.ToolMaterial IMMORTAL_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_immortal_diamond", 4, 2000, 10F, 3.5F, 8);

    public static ItemArmor.ArmorMaterial NATURAL_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_steel", EM.ID + ":steel", 20, new int[]{4, 7, 8, 4}, 8, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 0F);
    public static ItemArmor.ArmorMaterial PHANTOM_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_steel", EM.ID + ":steel", 20, new int[]{4, 7, 8, 4}, 8, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 0F);
    public static ItemArmor.ArmorMaterial DRAGON_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_steel", EM.ID + ":steel", 20, new int[]{4, 7, 8, 4}, 8, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 0F);
    public static ItemArmor.ArmorMaterial IMMORTAL_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_steel", EM.ID + ":steel", 20, new int[]{4, 7, 8, 4}, 8, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 0F);
}
