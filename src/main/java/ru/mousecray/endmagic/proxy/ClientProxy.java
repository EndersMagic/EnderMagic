package ru.mousecray.endmagic.proxy;

import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.components.ImageComponent;
import ru.mousecray.endmagic.api.embook.components.RecipeComponent;
import ru.mousecray.endmagic.api.embook.components.SmeltingRecipeComponent;
import ru.mousecray.endmagic.api.embook.components.TextComponent;
import ru.mousecray.endmagic.client.render.entity.RenderEnderArrow;
import ru.mousecray.endmagic.client.render.entity.RenderEntityCurseBush;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.TexturedModel;
import ru.mousecray.endmagic.client.render.tileentity.TileEntityPortalRenderer;
import ru.mousecray.endmagic.entity.EntityBluePearl;
import ru.mousecray.endmagic.entity.EntityCurseBush;
import ru.mousecray.endmagic.entity.EntityEnderArrow;
import ru.mousecray.endmagic.entity.EntityPurplePearl;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.items.ItemTextured;
import ru.mousecray.endmagic.network.ClientPacketHandler;
import ru.mousecray.endmagic.tileentity.portal.TilePortal;
import ru.mousecray.endmagic.util.RecipeHelper;
import ru.mousecray.endmagic.util.registry.IEMModel;

import java.util.*;
import java.util.function.Function;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class ClientProxy extends CommonProxy implements IModelRegistration {
    public ClientProxy() {
        addBakedModelOverride(ItemTextured.companion.simpletexturemodel, TexturedModel::new);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        PacketCustom.assignHandler(EM.ID, new ClientPacketHandler());
        RenderingRegistry.registerEntityRenderingHandler(EntityPurplePearl.class, manager -> new RenderSnowball(manager, EMItems.purpleEnderPearl, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityBluePearl.class, manager -> new RenderSnowball(manager, EMItems.blueEnderPearl, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderArrow.class, manager -> new RenderEnderArrow(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityCurseBush.class, manager -> new RenderEntityCurseBush(manager));
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TilePortal.class, new TileEntityPortalRenderer());
    }

    @GameRegistry.ObjectHolder(EM.ID + ":dragon_steel_pickaxe")
    public static Item steelPickaxe;

    @GameRegistry.ObjectHolder(EM.ID + ":immortal_diamond_hoe")
    public static Item diamondPickaxe;

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        //Add default book chapters
        BookApi.addStandartChapter("items", "ender_apple");
        BookApi.addStandartChapter("items", "book_of_the_end");
        BookApi.addStandartChapter("items", "ender_arrow", new RecipeComponent(new ItemStack(EMItems.enderArrow)));
        BookApi.addStandartChapter("items", "purple_pearl");
        BookApi.addStandartChapter("items", "blue_pearl");
        BookApi.addStandartChapter("items", "portal_coordinator", new RecipeComponent(new ItemStack(EMItems.itemPortalBinder)));
        BookApi.addStandartChapter("items", "ender_compass", new RecipeComponent(new ItemStack(EMItems.enderCompass)));
        BookApi.addChapter("items", "carbonic_materials",
                new TextComponent("book.chapter.text.carbonic_materials.1"),
                new SmeltingRecipeComponent(ImmutableList.of(
                        new ItemStack(EMItems.dragonCoal),
                        new ItemStack(EMItems.naturalCoal),
                        new ItemStack(EMItems.immortalCoal),
                        new ItemStack(EMItems.phantomCoal)),
                        ImmutableList.of(
                                new ItemStack(EMBlocks.enderLog, 1, 0),
                                new ItemStack(EMBlocks.enderLog, 1, 1),
                                new ItemStack(EMBlocks.enderLog, 1, 2),
                                new ItemStack(EMBlocks.enderLog, 1, 3)
                        ), "test"),
                new TextComponent("book.chapter.text.carbonic_materials.2"),
                recipesForItems(EMItems.steelToolsAndArmor()),
                new TextComponent("book.chapter.text.carbonic_materials.3"),
                recipesForItems(EMItems.diamondTools())
        );

        BookApi.addStandartChapter("blocks", "enderite_ore");
        BookApi.addStandartChapter("blocks", "blast_furnace", new RecipeComponent(new ItemStack(EMBlocks.blockBlastFurnace)));
        BookApi.addStandartChapter("blocks", "static_teleport", new RecipeComponent(new ItemStack(EMBlocks.blockMasterStaticPortal)));
        BookApi.addStandartChapter("blocks", "dark_teleport", new RecipeComponent(new ItemStack(EMBlocks.blockMasterDarkPortal)));
        BookApi.addStandartChapter("blocks", "portal_marker", new RecipeComponent(new ItemStack(EMBlocks.blockTopMark)));

        BookApi.addStandartChapter("plants", "ender_grass");
        BookApi.addStandartChapter("plants", "purple_pearl_sprout");
        BookApi.addStandartChapter("plants", "curse_bush");

        BookApi.addStandartChapter("mechanics", "compression_system",
                new ImageComponent(new ResourceLocation(EM.ID,"textures/book/compression_system_1.png"),""),
                new ImageComponent(new ResourceLocation(EM.ID,"textures/book/compression_system_2.png"),""),
                new ImageComponent(new ResourceLocation(EM.ID,"textures/book/compression_system_3.png"),"")
        );
        BookApi.addStandartChapter("mechanics", "teleport_construction");
    }

    private RecipeComponent recipesForItems(List<Item> items) {
        return new RecipeComponent(
                items.stream().map(ItemStack::new).collect(toImmutableList()),
                items.stream().map(r -> RecipeHelper.findRecipeGrid(new ItemStack(r))).collect(toImmutableList()), "");
    }

    private <A> ImmutableList<A> list(A... e) {
        return ImmutableList.copyOf(e);
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

    private Set<ResourceLocation> forRegister = new HashSet<>();

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