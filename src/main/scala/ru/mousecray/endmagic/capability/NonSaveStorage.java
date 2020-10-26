package ru.mousecray.endmagic.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class NonSaveStorage<A> implements Capability.IStorage<A> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<A> capability, A instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<A> capability, A instance, EnumFacing side, NBTBase nbt) {

    }
}
