package ru.mousecray.endmagic;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.mousecray.endmagic.proxy.CommonProxy;

@Mod(modid = EM.ID, name = EM.NAME, version = EM.VERSION)
public class EM {
    public static final String ID = "endmagic";
	public static final String VERSION = "@version@";
	public static final String NAME = "Ender's Magic";
	public static final String SERVER = "ru.mousecray.endmagic.proxy.CommonProxy";
	public static final String CLIENT = "ru.mousecray.endmagic.proxy.ClientProxy";
	
	@Instance(EM.ID)
	public static EM instance;
	
	@SidedProxy(clientSide=EM.CLIENT, serverSide=EM.SERVER)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {proxy.preInit(event);}
	@EventHandler
	public void init(FMLInitializationEvent event) {proxy.init(event);}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {proxy.postInit(event);}
}