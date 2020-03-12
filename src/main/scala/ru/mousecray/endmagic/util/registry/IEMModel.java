package ru.mousecray.endmagic.util.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;

/**
 * Interface for blocks with custom models
 */
public interface IEMModel {
	@SideOnly(Side.CLIENT) public default void registerModels(IModelRegistration modelRegistration) {}
}
