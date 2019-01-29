package ru.mousecray.endmagic.teleport;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(x).append(y).append(z).append(dim).hashCode();
    }

    @Override
    public String toString() {
        return "Location(xl = " + x + ", y = " + y + ", z = " + z + ")";
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound r = new NBTTagCompound();
        r.setInteger("xl", x);
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
        return
                (ImmutableList.of("xl", "y", "z", "dim").stream().map(tag::hasKey).reduce(true, (a, b) -> a && b))
                        ?
                        new Location(tag.getInteger("xl"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("dim"))
                        :
                        spawn;
    }

    public BlockPos toPos() {
        return new BlockPos(x, y, z);
    }

    public Location add(int i, int i1, int i2) {
        return new Location(x + i, y + i1, z + i2, dim);
    }
}
