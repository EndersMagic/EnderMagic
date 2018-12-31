package ru.mousecray.endmagic.teleport;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Location {

    public static Location spawn = new Location(0, 100, 0, 0);

    public final int x, y, z, dim;

    public Location(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }

    public Location(BlockPos pos, World world) {
        this(pos.getX(), pos.getY(), pos.getZ(), world.provider.getDimension());
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound r = new NBTTagCompound();
        r.setInteger("x", x);
        r.setInteger("y", y);
        r.setInteger("z", z);
        r.setInteger("dim", dim);
        return r;
    }

    public IBlockState getBlockState() {
        return getWorld().getBlockState(toPos());
    }

    public WorldServer getWorld() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dim);
    }

    public static Location fromNbt(NBTTagCompound tag) {
        return new Location(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("dim"));
    }

    public BlockPos toPos() {
        return new BlockPos(x, y, z);
    }
}
