package ru.mousecray.endmagic.proxy;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.util.NameUtils;

public class CommonProxy {

    protected List<Item> itemsToRegister = new LinkedList<>();
    protected List<Block> blocksToRegister = new LinkedList<>();

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        for (Field field : EMBlocks.class.getFields()) {
            try {
                Block block = (Block) field.get(null);

                String name = NameUtils.getName(block.getClass());
                block.setRegistryName(name);
                block.setUnlocalizedName(name);
                block.setCreativeTab(EM.EM_CREATIVE);

                registerBlock(block);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerBlock(Block block) {
        blocksToRegister.add(block);
        registerItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    private void registerItem(Item item) {

        String name = NameUtils.getName(item.getClass());
        if (item.getRegistryName() == null)
            item.setRegistryName(name);
        item.setUnlocalizedName(name);
        item.setCreativeTab(EM.EM_CREATIVE);

        itemsToRegister.add(item);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
        blocksToRegister.forEach(e.getRegistry()::register);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> e) {
        itemsToRegister.forEach(e.getRegistry()::register);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}