package ru.mousecray.endmagic.tileentity.portal;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TilePortal extends TileEntity {
    public int masterTileOffset;

    public void onEntityCollidedWithBlock(Entity entityIn) {
        if (!world.isRemote) {
            BlockPos masterPos = pos.down(masterTileOffset);
            TileEntity tileEntity = world.getTileEntity(masterPos);
            if (tileEntity instanceof TileMasterStaticPortal)
                ((TileMasterStaticPortal) tileEntity).addCollidedEntity(entityIn);
            else if (tileEntity instanceof TileMasterDarkPortal)
                ((TileMasterDarkPortal) tileEntity).addCollidedEntity(entityIn);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        masterTileOffset = compound.getInteger("masterTileOffset");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("masterTileOffset", masterTileOffset);
        return super.writeToNBT(compound);
    }
}
