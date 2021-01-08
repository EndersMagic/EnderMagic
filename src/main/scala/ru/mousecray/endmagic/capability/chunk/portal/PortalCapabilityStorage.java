package ru.mousecray.endmagic.capability.chunk.portal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PortalCapabilityStorage implements Capability.IStorage<PortalCapability> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<PortalCapability> capability, PortalCapability instance, EnumFacing side) {
        NBTTagList r = new NBTTagList();
        instance.masterPosToHeight.forEach((pos, height) -> {
            int encodedPos = (pos.getY() << 8) & ((pos.getX() % 16) << 4) & (pos.getZ() % 16);
            System.out.println("writeNBT " + encodedPos + " " + pos);
            r.appendTag(new NBTTagInt(encodedPos));
            r.appendTag(new NBTTagInt(height));
        });
        return r;
    }

    @Override
    public void readNBT(Capability<PortalCapability> capability, PortalCapability instance, EnumFacing side, NBTBase nbt) {
        instance.masterPosToHeight.clear();
        int startX = instance.chunk.x;
        int startZ = instance.chunk.z;

        NBTTagList nbtList = (NBTTagList) nbt;
        for (int i = 0; i < nbtList.tagCount(); i += 2) {
            int encodedPos = nbtList.getIntAt(i);
            int height = nbtList.getIntAt(i + 1);
            BlockPos pos = new BlockPos(startX + ((encodedPos >> 4) & 0xf), encodedPos >> 8, startZ + (encodedPos & 0xf));
            System.out.println("readNBT " + encodedPos + " " + pos);
            instance.masterPosToHeight.put(pos, height);
        }
    }
}
