package ru.mousecray.endmagic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3i;

public class TilePhantomAvoidingBlockBase extends TileEntity implements ITickable {
    public static int maxAvoidTicks = 90;

    public TilePhantomAvoidingBlockBase(int avoidTicks, Vec3i offsetFromSapling) {
        this.avoidTicks = avoidTicks;
        this.offsetFromSapling = offsetFromSapling;
    }

    public TilePhantomAvoidingBlockBase() {
        offsetFromSapling = new Vec3i(0, 0, 0);
        avoidTicks = 0;
    }

    public void teleportBlock(Vec3i offset) {
        IBlockState blockState = world.getBlockState(pos);
        world.setBlockToAir(pos);
        world.setBlockState(pos.add(offset), blockState);
        world.setTileEntity(pos.add(offset), new TilePhantomAvoidingBlockBase(avoidTicks, offsetFromSapling));
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

    public int avoidTicks;
    public int increment = 1;
    public Vec3i offsetFromSapling;

    @Override
    public void update() {
        if (avoidTicks >= maxAvoidTicks)
            increment = -1;
        else if (avoidTicks <= 0)
            increment = 1;
        avoidTicks += increment;
    }
}
