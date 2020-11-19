package ru.mousecray.endmagic.capability.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(IEmCapability.class)
    public static Capability<IEmCapability> emCapability;
    public static ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(IEmCapability.class));

    public static IEmCapability getCapa(EntityPlayer player) {
        return player.getCapability(emCapability, EnumFacing.UP);
    }


    private final IEmCapability value;

    public EmCapabilityProvider(EntityPlayer player) {
        value = new EmCapabilityCommon(player);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == emCapability && facing == EnumFacing.UP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == emCapability && facing == EnumFacing.UP ? emCapability.cast(value) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) emCapability.writeNBT(value, EnumFacing.UP);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        emCapability.readNBT(value, EnumFacing.UP, nbt);
    }
}
