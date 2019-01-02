package ru.mousecray.endmagic.tileentity;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class TileBlastFurnace extends EMTileEntity {
    public final InventoryBasic inv = new InventoryBasic("Wand Builder", true, 3);

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack item = inv.getStackInSlot(i);
            if (!item.isEmpty()) {
                NBTTagCompound comp = new NBTTagCompound();
                comp.setByte("Slot", (byte) i);
                item.writeToNBT(comp);
                list.appendTag(comp);
            }
        }
        tagCompound.setTag("Items", list);

        return super.writeToNBT(tagCompound);
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        NBTTagList list = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound comp = list.getCompoundTagAt(i);
            int j = comp.getByte("Slot") & 255;
            comp.removeTag("Slot");
            if (j >= 0 && j < inv.getSizeInventory()) inv.setInventorySlotContents(j, new ItemStack(comp));
        }

        super.readFromNBT(tagCompound);
    }
}
