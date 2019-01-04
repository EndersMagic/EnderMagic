package ru.mousecray.endmagic.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.client.render.entity.EMEntityThrowableRenderFactory;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.tileentity.TileEntityPortalRenderer;
import ru.mousecray.endmagic.entity.EntityBluePearl;
import ru.mousecray.endmagic.entity.EntityPurplePearl;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.tileentity.portal.TilePortal;
import ru.mousecray.endmagic.util.IEMModel;

public class ClientProxy extends CommonProxy implements IModelRegistration {

    private Map<ResourceLocation, Function<IBakedModel, IBakedModel>> bakedModelOverrides = new HashMap<>();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityPurplePearl.class, new EMEntityThrowableRenderFactory(EMItems.purpleEnderPearl));
        RenderingRegistry.registerEntityRenderingHandler(EntityBluePearl.class, new EMEntityThrowableRenderFactory(EMItems.blueEnderPearl));

    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TilePortal.class, new TileEntityPortalRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent e) {
        for (Block block : blocksToRegister) {
            if (block instanceof IEMModel) {
                ((IEMModel) block).registerModels(this);
            } else {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                        new ModelResourceLocation(block.getRegistryName(), "inventory"));
            }
        }

        for (Item item : itemsToRegister) {
            if (item instanceof IEMModel) {
                ((IEMModel) item).registerModels(this);
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0,
                        new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
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

    @Override
    public void setStateMapper(Block block, IStateMapper stateMapper) {
        ModelLoader.setCustomStateMapper(block, stateMapper);
    }
}