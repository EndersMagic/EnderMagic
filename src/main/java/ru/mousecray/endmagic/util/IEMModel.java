package ru.mousecray.endmagic.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.render.IModelRegistration;

/**
 * Interface for blocks with custom models
 */
public interface IEMModel {
	@SideOnly(Side.CLIENT) public void registerModels(IModelRegistration modelRegistration);
}
