package ru.mousecray.endmagic.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.mousecray.endmagic.entity.ListEntity;
import ru.mousecray.endmagic.tileentity.ListTile;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ListEntity.onRender();
		ListTile.onRender();
	}
	@Override
	public void init(FMLInitializationEvent event) {super.init(event);}
	@Override
	public void postInit(FMLPostInitializationEvent event) {super.postInit(event);}
}
