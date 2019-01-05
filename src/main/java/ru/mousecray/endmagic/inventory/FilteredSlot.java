package ru.mousecray.endmagic.inventory;

import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FilteredSlot extends Slot {

    private Set<Item> items;

    public FilteredSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Set<Item> items) {
        super(inventoryIn, index, xPosition, yPosition);
        this.items = items;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return items.contains(stack.getItem());
    }
}
