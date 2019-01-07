package ru.mousecray.endmagic.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import ru.mousecray.endmagic.EM;

public class EMMaterials {

    public static Item.ToolMaterial STEEL_TOOL_MATERIAL = EnumHelper.addToolMaterial("em_steel", 3, 500, 7F, 2.5F, 12);
    public static Item.ToolMaterial DIAMOND_TOOL_MATERIAL = EnumHelper.addToolMaterial("em_diamond", 4, 2000, 10F, 3.5F, 8);

    public static ItemArmor.ArmorMaterial STEEL_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("em_steel", EM.ID + ":steel", 20, new int[]{4, 7, 8, 4}, 8, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 0F);
    public static ItemArmor.ArmorMaterial DIAMOND_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("em_diamond", EM.ID + ":diamond", 38, new int[]{5, 8, 10, 5}, 9, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, 0F);
}
