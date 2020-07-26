package ru.mousecray.endmagic.items.tools;

import com.google.common.collect.ImmutableMap;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;
import ru.mousecray.endmagic.items.ItemTextured;

import java.util.Map;

public class EMArmor extends ItemArmor implements ItemTextured {

    private final String additionalName;
    private final ItemNamed reparItem;
    private final int color;

    public EMArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String additionalName, ItemNamed reparItem, int color) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.additionalName = additionalName;
        this.reparItem = reparItem;
        this.color = color;
    }

    @Override
    public String getCustomName() {
        return reparItem.getCustomName() + additionalName;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == reparItem || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public Map<String, Integer> textures() {
        return ImmutableMap.of(
                EM.ID + ":items/armor/colorless" + additionalName, color);
    }
}
