package ru.mousecray.endmagic.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.tileentity.TileBlastFurnace;

public class ContainerBlastFurnace extends Container {
    private EntityPlayer player;
    public TileBlastFurnace tile;

    public ContainerBlastFurnace(EntityPlayer player, TileBlastFurnace tile) {
        this.player = player;
        this.tile = tile;
        addSlotToContainer(new FilteredSlot(tile.inv, 0, 70, 70, EMBlocks.blockBlastFurnace.coalSet()));//coal
        addSlotToContainer(new FilteredSlot(tile.inv, 1, 70, 48, EMBlocks.blockBlastFurnace.ironSet()));//iron
        addSlotToContainer(new FilteredSlot(tile.inv, 2, 111, 59, EMBlocks.blockBlastFurnace.steelSet()));//steel

        for (int i = 0; i <= 2; i++)
            for (int j = 0; j <= 8; j++)
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 16 + j * 18, 151 + i * 18));

        for (int i = 0; i <= 8; i++)
            addSlotToContainer(new Slot(player.inventory, i, 16 + i * 18, 209));
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotNumber) {
        System.out.println(slotNumber);
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotNumber);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (slotNumber < 3) {
                if (!mergeItemStack(itemstack1, 3, 38, true)) {
                    return ItemStack.EMPTY;
                } else {
                    slot.onSlotChange(itemstack1, itemstack);
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        } else return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}