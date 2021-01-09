package ru.mousecray.endmagic.capability.chunk.portal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
            NBTTagCompound entry = new NBTTagCompound();
            entry.setInteger("x", pos.getX());
            entry.setInteger("y", pos.getY());
            entry.setInteger("z", pos.getZ());
            entry.setInteger("height", height);
            r.appendTag(entry);
        });
        return r;
    }

    @Override
    public void readNBT(Capability<PortalCapability> capability, PortalCapability instance, EnumFacing side, NBTBase nbt) {
        instance.masterPosToHeight.clear();

        NBTTagList nbtList = (NBTTagList) nbt;

        for (int i = 0; i < nbtList.tagCount(); i += 2) {
            NBTTagCompound entry = nbtList.getCompoundTagAt(i);
            instance.masterPosToHeight.put(new BlockPos(entry.getInteger("x"), entry.getInteger("y"), entry.getInteger("z")), entry.getInteger("height"));
        }
    }
}
