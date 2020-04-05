package ru.mousecray.endmagic.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.nbt.NBTTagCompound;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.IRuneChunkCapability;
import ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider;

import static ru.mousecray.endmagic.capability.chunk.RuneStateCapabilityProvider.*;

public enum PacketTypes {
    UPDATE_COMPAS_TARGET,
    UPDATE_PHANROM_AVOIDINCAPABILITY,
    SYNC_RUNE_CAPABILITY {
        public PacketCustom prepare(int chunkX, int chunkZ, IRuneChunkCapability capability) {
            return packet()
                    .writeInt(chunkX)
                    .writeInt(chunkZ)
                    .writeNBTTagCompound((NBTTagCompound) runeStateCapability.writeNBT(capability, null));
        }

    };

    public int id = ordinal() + 1;

    public static PacketTypes valueOf(int id) {
        return values()[id - 1];
    }

    public PacketCustom packet() {
        return new PacketCustom(EM.ID, id);
    }
}