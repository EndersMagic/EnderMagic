package ru.mousecray.endmagic.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.blocks.ListBlock;
import ru.mousecray.endmagic.entity.ListEntity;
import ru.mousecray.endmagic.init.Recipes;
import ru.mousecray.endmagic.init.RegModels;
import ru.mousecray.endmagic.items.ListItem;
import ru.mousecray.endmagic.tileentity.ListTile;
import ru.mousecray.endmagic.world.gen.feature.TPWorldGenerator;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		ListBlock.onRegister();
		ListItem.onRegister();
		ListEntity.onRegister();
		ListTile.onRegister();
		
		MinecraftForge.EVENT_BUS.register(new RegModels());
	}
	public void init(FMLInitializationEvent event) {
		Recipes.init();
		GameRegistry.registerWorldGenerator(new TPWorldGenerator(), 0);
	}
	public void postInit(FMLPostInitializationEvent event) {}
}
