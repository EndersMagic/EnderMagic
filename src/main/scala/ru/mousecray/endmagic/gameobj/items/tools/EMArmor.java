package ru.mousecray.endmagic.gameobj.items.tools;

import com.google.common.collect.ImmutableMap;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.gameobj.items.ItemTextured;

import java.util.Map;

public class EMArmor extends ItemArmor implements ItemTextured {

    private final String name;
    private final Item reparItem;
    private final int color;

    public EMArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String name, Item reparItem, int color) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.name = name;
        this.reparItem = reparItem;
        this.color = color;
    }

    @Override
    public String getCustomName() {
        return name;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == reparItem || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public Map<String, Integer> textures() {
        return ImmutableMap.of(
                EM.ID + ":items/armor/colorless_" + name.substring(name.lastIndexOf('_') + 1), color);
    }
}
