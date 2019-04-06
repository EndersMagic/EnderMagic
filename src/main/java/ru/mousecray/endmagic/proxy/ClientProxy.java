package ru.mousecray.endmagic.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.components.TextComponent;
import ru.mousecray.endmagic.client.render.entity.EMEntityThrowableRenderFactory;
import ru.mousecray.endmagic.client.render.entity.RenderEnderArrow;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.TexturedModel;
import ru.mousecray.endmagic.client.render.tileentity.TileEntityPortalRenderer;
import ru.mousecray.endmagic.entity.EntityBluePearl;
import ru.mousecray.endmagic.entity.EntityEnderArrow;
import ru.mousecray.endmagic.entity.EntityPurplePearl;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.items.ItemTextured;
import ru.mousecray.endmagic.tileentity.portal.TilePortal;
import ru.mousecray.endmagic.util.IEMModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ClientProxy extends CommonProxy implements IModelRegistration {
    public ClientProxy() {
        addBakedModelOverride(ItemTextured.companion.simpletexturemodel, TexturedModel::new);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityPurplePearl.class, new EMEntityThrowableRenderFactory(EMItems.purpleEnderPearl));
        RenderingRegistry.registerEntityRenderingHandler(EntityBluePearl.class, new EMEntityThrowableRenderFactory(EMItems.blueEnderPearl));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderArrow.class, manager -> new RenderEnderArrow(manager));
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TilePortal.class, new TileEntityPortalRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        //Add default book chapters
        BookApi.addChapter("Test", new TextComponent("book.test"));
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

    private ArrayList<ResourceLocation> forRegister = new ArrayList<>();

    private Map<ModelResourceLocation, Function<IBakedModel, IBakedModel>> bakedModelOverrides = new HashMap<>();
    private Map<ResourceLocation, Function<IBakedModel, IBakedModel>> bakedModelOverridesR = new HashMap<>();

    @Override
    public void registerTexture(ResourceLocation resourceLocation) {
        forRegister.add(resourceLocation);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void stitcherEventPre(TextureStitchEvent.Pre event) {
        forRegister.forEach(event.getMap()::registerSprite);
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent e) {
        for (Map.Entry<ModelResourceLocation, Function<IBakedModel, IBakedModel>> override : bakedModelOverrides.entrySet()) {
            ModelResourceLocation resource = override.getKey();
            IBakedModel existingModel = e.getModelRegistry().getObject(resource);
            e.getModelRegistry().putObject(resource, override.getValue().apply(existingModel));
        }
        for (ModelResourceLocation resource : e.getModelRegistry().getKeys()) {
            ResourceLocation key = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath());

            if (bakedModelOverridesR.containsKey(key)) {
                System.out.println(resource);
                e.getModelRegistry().putObject(resource, bakedModelOverridesR.get(key).apply(e.getModelRegistry().getObject(resource)));
            }
        }
    }

    @Override
    public void addBakedModelOverride(ModelResourceLocation resource, Function<IBakedModel, IBakedModel> override) {
        bakedModelOverrides.put(resource, override);
    }

    @Override
    public void addBakedModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override) {
        bakedModelOverridesR.put(resource, override);
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