package ru.mousecray.endmagic.capability.world;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PhantomAvoidingGroupCapabilityProvider implements ICapabilityProvider {

    @CapabilityInject(PhantomAvoidingGroupCapability.class)
    public static Capability<PhantomAvoidingGroupCapability> avoidingGroupCapability;
    public static ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(PhantomAvoidingGroupCapability.class));

    PhantomAvoidingGroupCapability value = new PhantomAvoidingGroupCapability();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == avoidingGroupCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == avoidingGroupCapability ? avoidingGroupCapability.cast(value) : null;
    }
}
