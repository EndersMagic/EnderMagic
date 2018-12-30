package ru.mousecray.endmagic.teleport;

import net.minecraft.nbt.NBTTagCompound;

public class Location {
    final int x, y, z, dim;

    public Location(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
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
