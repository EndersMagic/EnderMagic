package ru.mousecray.endmagic.capability.entity;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EndermanHeadBlockCapabilityProvider implements ICapabilityProvider, INBTSerializable {

    @CapabilityInject(EndermanHeadBlockCapability.class)
    public static Capability<EndermanHeadBlockCapability> endermanHeadBlockCapability;
    public static ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(EndermanHeadBlockCapability.class));

    EndermanHeadBlockCapability value = new EndermanHeadBlockCapability();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == endermanHeadBlockCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == endermanHeadBlockCapability ? endermanHeadBlockCapability.cast(value) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return endermanHeadBlockCapability.getStorage().writeNBT(endermanHeadBlockCapability, value, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        endermanHeadBlockCapability.getStorage().readNBT(endermanHeadBlockCapability, value, null, nbt);
    }
}
