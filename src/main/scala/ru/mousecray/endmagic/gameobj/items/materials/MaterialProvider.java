package ru.mousecray.endmagic.gameobj.items.materials;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public interface MaterialProvider {
    Item.ToolMaterial material();

    ItemArmor.ArmorMaterial armorMaterial();
}
