package ru.mousecray.endmagic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import javax.annotation.Nullable;

public class TilePhantomAvoidingBlockBase extends TileEntity {
    public static int maxAvoidTicks = 20*5;

    public TilePhantomAvoidingBlockBase(int avoidTicks, Vec3i offsetFromSapling) {
        this.avoidTicks = avoidTicks;
        this.offsetFromSapling = offsetFromSapling;
    }

    public TilePhantomAvoidingBlockBase() {
        this(0, new Vec3i(0, 0, 0));
    }

    public BlockPos teleportBlock(Vec3i offset) {
        IBlockState blockState = world.getBlockState(pos);
        world.setBlockToAir(pos);
        BlockPos newPos = pos.add(offset);
        world.setBlockState(newPos, blockState);
        world.setTileEntity(newPos, new TilePhantomAvoidingBlockBase(avoidTicks, offsetFromSapling));
        return newPos;
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

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    public Vec3i offsetFromSapling;
    public int avoidTicks;
    public int increment = 1;
}
