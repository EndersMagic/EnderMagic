package ru.mousecray.endmagic.items.tools;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.util.NameProvider;

public class EMArmor extends ItemArmor implements NameProvider {

    private String name;
    private Item reparItem;

    public EMArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String name, Item reparItem) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.name = name;
        this.reparItem = reparItem;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == reparItem || super.getIsRepairable(toRepair, repair);
    }
}
