package ru.mousecray.endmagic.capability.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import ru.mousecray.endmagic.rune.RuneColor;

import javax.annotation.Nullable;

public class EmCapabilityStorage implements Capability.IStorage<EmCapability> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<EmCapability> capability, EmCapability instance, EnumFacing side) {
        NBTTagCompound r = new NBTTagCompound();
        for (RuneColor color : RuneColor.values()) {
            r.setInteger(color.name(), instance.getEm(color));
            r.setInteger(color.name() + "Max", instance.getMaxEm(color));
        }
        return r;
    }

    @Override
    public void readNBT(Capability<EmCapability> capability, EmCapability instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        for (RuneColor color : RuneColor.values()) {
            instance.em.put(color, tag.getInteger(color.name()));
            instance.maxEm.put(color, tag.getInteger(color.name() + "Max"));
        }
    }
}
