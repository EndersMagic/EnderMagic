package ru.mousecray.endmagic.capability.chunk;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RuneStateCapabilityProvider implements ICapabilityProvider {

    @CapabilityInject(RuneChunkCapability.class)
    public static Capability<RuneChunkCapability> runeStateCapability;
    public static ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(RuneChunkCapability.class));


    RuneChunkCapability value = new RuneChunkCapability();

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