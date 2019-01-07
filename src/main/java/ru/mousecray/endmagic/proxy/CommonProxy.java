package ru.mousecray.endmagic.proxy;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMEntities;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.init.EMMaterials;
import ru.mousecray.endmagic.inventory.ContainerBlastFurnace;
import ru.mousecray.endmagic.inventory.GuiBlasFurnace;
import ru.mousecray.endmagic.util.NameAndTabUtils;

public class CommonProxy implements IGuiHandler {

    protected List<Item> itemsToRegister = new LinkedList<>();
    protected List<Class<? extends TileEntity>> tilesToRegister = new LinkedList<>();
    protected List<Block> blocksToRegister = new LinkedList<>();
    protected List<EntityEntry> entityToRegister = new LinkedList<>();

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        //Registration Blocks
        for (Field field : EMBlocks.class.getFields()) {
            try {
                Block block = (Block) field.get(null);
                registerBlock(block);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //Registration Items
        for (Field field : EMItems.class.getFields()) {
            try {
                Item item = (Item) field.get(null);
                registerItem(item);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        //Registration Entity
        for (Field field : EMEntities.class.getFields()) {
            try {
                EntityEntry entity = (EntityEntry) field.get(null);
                entityToRegister.add(entity);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        NetworkRegistry.INSTANCE.registerGuiHandler(EM.instance, this);
    }

    private void registerBlock(Block block) {
        String name = NameAndTabUtils.getName(block);
        block.setRegistryName(name);
        block.setUnlocalizedName(name);
        block.setCreativeTab(NameAndTabUtils.getCTab(block));

        if (block instanceof ITileEntityProvider) {
            Class<? extends TileEntity> tile = ((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass();
            registerTile(tile);
        }

        blocksToRegister.add(block);
        registerItem(new ItemBlock(block), block.getRegistryName().toString());
    }

    private void registerTile(Class<? extends TileEntity> tile) {
        tilesToRegister.add(tile);
    }


    private void registerItem(Item item, String name) {
        item.setRegistryName(name);
        item.setUnlocalizedName(name);
        item.setCreativeTab(NameAndTabUtils.getCTab(item));

        itemsToRegister.add(item);
    }

    private void registerItem(Item item) {
        registerItem(item, NameAndTabUtils.getName(item));
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
        blocksToRegister.forEach(e.getRegistry()::register);
        tilesToRegister.forEach(tile -> GameRegistry.registerTileEntity(tile, new ResourceLocation(EM.ID, tile.getSimpleName())));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> e) {
        itemsToRegister.forEach(e.getRegistry()::register);
    }
    
    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> e) {
    	entityToRegister.forEach(e.getRegistry()::register);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public static int blastFurnaceGui = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == blastFurnaceGui)
            return new ContainerBlastFurnace(player,EMBlocks.blockBlastFurnace.tile(world, new BlockPos(x, y, z)));

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == blastFurnaceGui)
            return new GuiBlasFurnace(new ContainerBlastFurnace(player, EMBlocks.blockBlastFurnace.tile(world, new BlockPos(x, y, z))));

        return null;
    }
}