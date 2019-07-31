package ru.mousecray.endmagic.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import ru.mousecray.endmagic.EM;

public class EMMaterials {

    public static Item.ToolMaterial NATURAL_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_natural_steel", 2, 500, 7, 2, 12);
    public static Item.ToolMaterial PHANTOM_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_phantom_steel", 3, 1000, 7, 2.5F, 12);
    public static Item.ToolMaterial DRAGON_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_dragon_steel", 3, 1500, 8, 3, 12);
    public static Item.ToolMaterial IMMORTAL_STEEL_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_immortal_steel", 3, 2000, 7, 4F, 12);

    public static Item.ToolMaterial NATURAL_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_natural_diamond", 3, 1000, 8, 3F, 20);
    public static Item.ToolMaterial PHANTOM_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_phantom_diamond", 4, 2000, 9, 3.5F, 20);
    public static Item.ToolMaterial DRAGON_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_dragon_diamond", 4, 3000, 10, 4F, 22);
    public static Item.ToolMaterial IMMORTAL_DIAMOND_TOOL_MATERIAL =
            EnumHelper.addToolMaterial("em_immortal_diamond", 5, 4000, 12, 7, 22);

    public static ItemArmor.ArmorMaterial NATURAL_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_natural_steel", EM.ID + ":natural_steel", 16, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial PHANTOM_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_phantom_steel", EM.ID + ":phantom_steel", 17, new int[]{2, 5, 7, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial DRAGON_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_dragon_steel", EM.ID + ":dragon_steel", 25, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 1);
    public static ItemArmor.ArmorMaterial IMMORTAL_STEEL_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("em_immortal_steel", EM.ID + ":immortal_steel", 30, new int[]{4, 7, 9, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2);
}
