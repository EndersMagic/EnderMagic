package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RuneStateCapabilityProvider implements ICapabilityProvider {

    @CapabilityInject(IRuneChunkCapability.class)
    public static Capability<IRuneChunkCapability> runeStateCapability;
    public static ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(IRuneChunkCapability.class));


    private final IRuneChunkCapability value = new CommonRuneChunkCapability();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == runeStateCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == runeStateCapability ? runeStateCapability.cast(value) : null;
    }
}