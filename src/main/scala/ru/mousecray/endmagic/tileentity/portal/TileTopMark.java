package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileTopMark extends TileEntity {
    int masterTileOffset = 0;

    public void neighborChanged() {
        TileEntity tileEntity = world.getTileEntity(pos.down(masterTileOffset));
        if (tileEntity instanceof TileMasterBasePortal)
            ((TileMasterBasePortal) tileEntity).notifyTopCapUpdate(pos);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        masterTileOffset = compound.getByte("masterTileOffset");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte("masterTileOffset", (byte) masterTileOffset);
        return super.writeToNBT(compound);
    }
}
