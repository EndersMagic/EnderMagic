package ru.mousecray.endmagic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3i;

public class TilePhantomAvoidingBlockBase extends TileEntity {
    public int avoidTicks = 0;
    public Vec3i offsetFromSapling = new Vec3i(0, 0, 0);

    public void teleportBlock(Vec3i offset) {
        IBlockState blockState = world.getBlockState(pos);
        world.setBlockToAir(pos);
        world.setBlockState(pos.add(offset), blockState);
        validate();
        world.setTileEntity(pos.add(offset), this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        avoidTicks = compound.getInteger("avoidTicks");
        offsetFromSapling = new Vec3i(compound.getInteger("offsetFromSaplingX"), compound.getInteger("offsetFromSaplingY"), compound.getInteger("offsetFromSaplingZ"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("avoidTicks", avoidTicks);
        compound.setInteger("offsetFromSaplingX", offsetFromSapling.getX());
        compound.setInteger("offsetFromSaplingY", offsetFromSapling.getY());
        compound.setInteger("offsetFromSaplingZ", offsetFromSapling.getZ());
        return super.writeToNBT(compound);
    }
}
