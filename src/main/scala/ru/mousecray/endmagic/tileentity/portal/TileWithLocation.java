package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.nbt.NBTTagCompound;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.EMTileEntity;

import static ru.mousecray.endmagic.teleport.Location.spawn;

public class TileWithLocation extends EMTileEntity {
    public Location destination = spawn;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("destination", destination.toNbt());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("destination", 10))
            destination = Location.fromNbt(compound.getCompoundTag("destination"));
        else
            destination = spawn;

        super.readFromNBT(compound);
    }
}
