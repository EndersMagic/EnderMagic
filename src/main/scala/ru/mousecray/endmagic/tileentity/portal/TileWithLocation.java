package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.nbt.NBTTagCompound;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.tileentity.EMTileEntity;

import static ru.mousecray.endmagic.teleport.Location.spawn;

public class TileWithLocation extends EMTileEntity {
    public Location getDistination() {
        return distination;
    }

    public void updateDistination(Location readFromItem) {
        distination = readFromItem;
    }

    protected Location distination = spawn;

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
            distination = spawn;

        super.readFromNBT(compound);
    }
}
