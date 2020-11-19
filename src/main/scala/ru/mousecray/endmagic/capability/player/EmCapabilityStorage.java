package ru.mousecray.endmagic.capability.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EmCapabilityStorage implements Capability.IStorage<IEmCapability> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IEmCapability> capability, IEmCapability instance, EnumFacing side) {
        NBTTagCompound r = new NBTTagCompound();
        r.setInteger("em", instance.getEm());
        return r;
    }

    @Override
    public void readNBT(Capability<IEmCapability> capability, IEmCapability instance, EnumFacing side, NBTBase nbt) {
        instance.setEm(((NBTTagCompound) nbt).getInteger("em"));
    }
}
