package ru.mousecray.endmagic.init;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.blocks.ListBlock;
import ru.mousecray.endmagic.client.renderer.IModelRegistration;
import ru.mousecray.endmagic.client.renderer.model.BakedModelFullbright;
import ru.mousecray.endmagic.items.ListItem;

public class RegModels implements IModelRegistration {
	
    private Map<ResourceLocation, Function<IBakedModel, IBakedModel>> bakedModelOverrides = new HashMap<>();
	
	@SubscribeEvent
	public void regiserModels(ModelRegistryEvent event) {
		ListItem.onRender();
		ListBlock.onRender();
		ListBlock.ENDER_GRASS.registerModels(this);
	}
	
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent e) {
//        for (ModelResourceLocation resource : e.getModelRegistry().getKeys()) {
//            ResourceLocation key = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath());
//            e.getModelRegistry().putObject(resource, bakedModelOverrides.get(key).apply(e.getModelRegistry().getObject(resource)));
//        }
        e.getModelRegistry().putObject(new ModelResourceLocation("ender_grass", "normal"), new BakedModelFullbright(e.getModelManager().getModel(new ModelResourceLocation("ender_grass", "normal")), EndMagicData.ID + ":blocks/ender_grass"));
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

    @Override
    public void setModelVariants(Item item, ResourceLocation... variants) {
        ModelBakery.registerItemVariants(item, variants);
    }

    @Override
    public void setModelMeshDefinition(Block block, ItemMeshDefinition meshDefinition) {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), meshDefinition);
    }

    @Override
    public void addModelLoader(Supplier<ICustomModelLoader> modelLoader) {
        ModelLoaderRegistry.registerLoader(modelLoader.get());
    }

    @Override
    public void setStateMapper(Block block, IStateMapper stateMapper) {
        ModelLoader.setCustomStateMapper(block, stateMapper);
    }

    @Override
    public void setTesr(Class<? extends TileEntity> tile, TileEntitySpecialRenderer tesr) {
        ClientRegistry.bindTileEntitySpecialRenderer(tile, tesr);
    }
}
