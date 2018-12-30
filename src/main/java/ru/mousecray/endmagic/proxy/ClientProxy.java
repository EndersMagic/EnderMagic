package ru.mousecray.endmagic.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.render.IModelRegistration;
import ru.mousecray.endmagic.util.IEMModel;

public class ClientProxy extends CommonProxy implements IModelRegistration {
	
	private Map<ResourceLocation, Function<IBakedModel, IBakedModel>> bakedModelOverrides = new HashMap<>();
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {super.preInit(event);}
	@Override
	public void init(FMLInitializationEvent event) {super.init(event);}
	@Override
	public void postInit(FMLPostInitializationEvent event) {super.postInit(event);}
	
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent e) {
		 for (Block block : blocksToRegister) {
			 if(block instanceof IEMModel) {
				 ((IEMModel) block).registerModels(this);
			 }
		 }
		 
//		 for (Item item : itemsToRegister) {
//			 if (item instanceof IEMItem) {
//				 ((IEMItem) item).registerModels(this);
//			 }
//		 }
	}

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent e) {
		for (ModelResourceLocation resource : e.getModelRegistry().getKeys()) {
			ResourceLocation key = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath());

			if (bakedModelOverrides.containsKey(key)) {
				e.getModelRegistry().putObject(resource, bakedModelOverrides.get(key).apply(e.getModelRegistry().getObject(resource)));
			}
		}
	}
    @Override
    public void addBakedModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override) {
        bakedModelOverrides.put(resource, override);
    }

    @Override
    public void setModel(Block block, int meta, ModelResourceLocation resource) {
        setModel(Item.getItemFromBlock(block), meta, resource);
    }

    @Override
    public void setModel(Item item, int meta, ModelResourceLocation resource) {
        ModelLoader.setCustomModelResourceLocation(item, meta, resource);
    }
}