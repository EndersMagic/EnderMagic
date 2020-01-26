package ru.mousecray.endmagic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

public class TilePhantomAvoidingBlockBase extends TileEntity {
    public static int maxAvoidTicks = 90;

    public TilePhantomAvoidingBlockBase(int avoidTicks, Vec3i offsetFromSapling) {
        this.avoidTicks = avoidTicks;
        this.offsetFromSapling = offsetFromSapling;
    }

    public TilePhantomAvoidingBlockBase() {
        this(0, new Vec3i(0, 0, 0));
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

    private void sendUpdates() {
        if (world instanceof WorldServer) {
            PlayerChunkMapEntry chunk = ((WorldServer) world).getPlayerChunkMap().getEntry(pos.getX() >> 4, pos.getZ() >> 4);
            if (chunk != null) chunk.sendPacket(getUpdatePacket());
        }
    }

    public Vec3i offsetFromSapling;
    public int avoidTicks;
    public int increment = 1;
}
