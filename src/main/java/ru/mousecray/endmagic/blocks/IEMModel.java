package ru.mousecray.endmagic.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.client.renderer.IModelRegistration;

public interface IEMModel {
	
    @SideOnly(Side.CLIENT)
    public void registerModels(IModelRegistration modelRegistration);
}
