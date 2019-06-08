package ru.mousecray.endmagic.tileentity;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.Optional;

public class TileBlastFurnace extends EMTileEntity implements ITickable {
    public final InventoryBasic inv = new InventoryBasic("Wand Builder", true, 3);

    private Optional<Item> result = Optional.empty();
    private int tickProcess = 0;

    public TileBlastFurnace() {
        inv.addInventoryChangeListener(invBasic -> result = EMBlocks.blockBlastFurnace.matchRecipe(inv.getStackInSlot(0).getItem(), inv.getStackInSlot(1).getItem()));
    }

    @Override
    public void update() {
        if (result.isPresent()) {
            ItemStack steel = inv.getStackInSlot(2);
            Item r = result.get();
            if (steel.getItem() == r || steel == ItemStack.EMPTY) {
                tickProcess++;

                spawnParticles();

                if (tickProcess >= 20 * 60 * 5) {
                    tickProcess = 0;
                    inv.decrStackSize(0, 1);
                    inv.decrStackSize(1, 1);
                    if (steel == ItemStack.EMPTY)
                        inv.setInventorySlotContents(2, new ItemStack(r));
                    else
                        steel.setCount(steel.getCount() + 1);
                }
            }
        } else
            tickProcess = 0;

    }

    public void spawnParticles() {
        EnumFacing side = EnumFacing.HORIZONTALS[world.rand.nextInt(EnumFacing.HORIZONTALS.length)];

        world.spawnParticle(EnumParticleTypes.LAVA, pos.getX(), pos.getY(), pos.getZ(), side.getDirectionVec().getX(), 0, side.getDirectionVec().getZ());
    }

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
        tagCompound.setInteger("tickProcess", tickProcess);

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
        tickProcess = tagCompound.getInteger("tickProcess");

        super.readFromNBT(tagCompound);
    }
}
