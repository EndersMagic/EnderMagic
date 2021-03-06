package ru.mousecray.endmagic.util.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;

import javax.annotation.Nullable;

public interface IExtendedProperties {
    @SideOnly(Side.CLIENT)
    default void registerModels(IModelRegistration modelRegistration) {}

    @Nullable
    default String getCustomName() {
        return NameAndTabUtils.getName(this);
    }

    @Nullable
    default CreativeTabs getCustomCreativeTab() {
        return EM.EM_CREATIVE;
    }
}
