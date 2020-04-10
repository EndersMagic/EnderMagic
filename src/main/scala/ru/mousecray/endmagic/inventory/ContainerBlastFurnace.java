package ru.mousecray.endmagic.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.tileentity.TileBlastFurnace;

public class ContainerBlastFurnace extends Container {
    public TileBlastFurnace tile;

    public ContainerBlastFurnace(EntityPlayer player, TileBlastFurnace tile) {
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

    @Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index < 3){
                if (!mergeItemStack(itemstack1, 3, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(!mergeItemStack(itemstack1,0,3,false)){
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.getPos().distanceSq(playerIn.getPosition()) < 16*16//для Baubles (16 блоков от тайла)
                && playerIn.world.getTileEntity(tile.getPos()) == tile;
    }
}