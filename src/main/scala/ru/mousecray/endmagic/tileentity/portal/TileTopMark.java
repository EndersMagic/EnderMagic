package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.mousecray.endmagic.tileentity.ByBlockNotifiable;

public class TileTopMark extends TileEntity implements ByBlockNotifiable {
    int masterTileOffset = 0;

    @Override
    public void neighborChanged() {
        System.out.println("at top " + pos.down(masterTileOffset));
        TileEntity tileEntity = world.getTileEntity(pos.down(masterTileOffset));
        if (tileEntity instanceof TileMasterBasePortal)
            ((TileMasterBasePortal) tileEntity).notifyTopCapUpdate(pos);
    }

    @Override
    public void breakBlock() {
        TileEntity tileEntity = world.getTileEntity(pos.down(masterTileOffset));
        if (tileEntity instanceof TileMasterBasePortal)
            ((TileMasterBasePortal) tileEntity).closePortal();
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
