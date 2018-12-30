package ru.mousecray.endmagic.teleport;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.portal.TilePortal;

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

    public static Location fromNbt(NBTTagCompound tag) {
        return new Location(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("dim"));
    }
}
