package ru.mousecray.endmagic.capability.world;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PhantomTreeCapabilityProvider implements ICapabilityProvider {
    public static final ResourceLocation name = new ResourceLocation(EM.ID, NameAndTabUtils.getName(PhantomTreeCapability.class));
    private final PhantomTreeCapability myImpl;

    @CapabilityInject(PhantomTreeCapability.class)
    public static Capability<PhantomTreeCapability> phantomTreeCapability = null;

    public PhantomTreeCapabilityProvider(World world) {
        myImpl = new PhantomTreeCapability(world);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == phantomTreeCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return phantomTreeCapability.cast(myImpl);
    }
}
