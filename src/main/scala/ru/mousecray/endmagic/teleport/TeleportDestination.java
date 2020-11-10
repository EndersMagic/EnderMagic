package ru.mousecray.endmagic.teleport;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author WilliHay
 *
 */
public class TeleportDestination implements INBTSerializable<NBTTagCompound>
{
    public enum DestinationType
    {
        BLOCKPOS(0, "Block"),
        SPAWNBED(1, "Spawn Bed"),
        BEACON(2, "Beacon"),
        RAIL(3, "Rail"),
        CUBE(4, "Cube");

        private final int destinationValue;
        private final String valueName;

        private DestinationType(int destinationValue, String name)
        {
            this.destinationValue = destinationValue;
            this.valueName = name;
        }

        @Override
        public String toString()
        {
            return this.valueName;
        }

        int getDestinationValue()
        {
            return destinationValue;
        }
    }

    public DestinationType destinationType;
    public int dimension;
    public String friendlyName;
    public BlockPos position;
    private UUID uuid;

    /** TODO: this field is planned for future use
     *  FLAG BIT MEANINGS:
     *  1: INVALID  - last validation check returned 'invalid'
     *  2: MOVED    - last validation check updated the position of this destination with a new, valid position
     *  4: RENAMED  - last validation check updated the name of this destination with a new name
     */
    private int flags;

    public TeleportDestination(NBTTagCompound nbt)
    {
        deserializeNBT(nbt);
    }

    public TeleportDestination(@Nonnull String friendlyName, DestinationType destinationType, int dimension, @Nonnull BlockPos position)
    {
        this(UUID.randomUUID(), friendlyName, destinationType, dimension, position);
    }

    public TeleportDestination(@Nonnull UUID uuid, @Nonnull String friendlyName, DestinationType destinationType, int dimension, @Nonnull BlockPos position)
    {
        this.uuid = uuid;
        this.friendlyName = friendlyName;
        this.destinationType = destinationType;
        this.dimension = dimension;
        this.position = position;
        this.flags = 0;

        if (this.uuid == null)
        {
            this.uuid = new UUID(0, 0);
        }

        if (this.friendlyName == null)
        {
            this.friendlyName = "<Invalid Destination Name>";
        }

        if (this.position == null)
        {
            this.position = BlockPos.ORIGIN;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof TeleportDestination))
        {
            return false;
        }
        else
        {
            return this.uuid != null && this.uuid.equals(((TeleportDestination)obj).getUUID());
        }
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("Type", destinationType)
                .add("UUID", uuid)
                .add("Name", friendlyName)
                .add("Dimension", dimension)
                .add("Position", position)
                .add("Flags", Integer.toBinaryString(flags))
                .toString();
    }

    public UUID getUUID()
    {
        return uuid;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("DestinationType", destinationType.getDestinationValue());
        nbt.setInteger("Dimension", dimension);
        nbt.setString("FriendlyName", friendlyName);
        nbt.setLong("Position", position.toLong());
        nbt.setUniqueId("UUID", uuid);
        nbt.setInteger("Flags", flags);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        destinationType = DestinationType.values()[nbt.getInteger("DestinationType")];
        dimension = nbt.getInteger("Dimension");
        friendlyName = nbt.getString("FriendlyName");
        position = BlockPos.fromLong(nbt.getLong("Position"));
        uuid = nbt.getUniqueId("UUID");
        flags = nbt.getInteger("Flags");
    }
}