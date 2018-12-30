package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.mousecray.endmagic.teleport.Location;

public class TileWithLocation extends TileEntity {
    Location distination;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("distination", distination.toNbt());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("distination", 10))
            distination = Location.fromNbt(compound.getCompoundTag("distination"));
        else
            distination = new Location(pos, world);

        super.readFromNBT(compound);
    }
}
