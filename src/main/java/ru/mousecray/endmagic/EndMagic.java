package ru.mousecray.endmagic;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.mousecray.endmagic.proxy.CommonProxy;

@Mod(modid=EndMagicData.ID, name=EndMagicData.NAME, version=EndMagicData.VERSION)
public class EndMagic {
	
	@Instance(EndMagicData.ID)
	public static EndMagic instance;
	
	@SidedProxy(clientSide=EndMagicData.CLIENT, serverSide=EndMagicData.SERVER)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {proxy.preInit(event);}
	@EventHandler
	public void init(FMLInitializationEvent event) {proxy.init(event);}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {proxy.postInit(event);}
}
