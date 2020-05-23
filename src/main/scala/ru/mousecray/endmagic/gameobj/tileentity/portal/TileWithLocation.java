package ru.mousecray.endmagic.gameobj.tileentity.portal;

import net.minecraft.nbt.NBTTagCompound;
import ru.mousecray.endmagic.gameobj.tileentity.EMTileEntity;
import ru.mousecray.endmagic.util.teleport.Location;

import static ru.mousecray.endmagic.util.teleport.Location.spawn;

public class TileWithLocation extends EMTileEntity {
    public Location distination = spawn;

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
