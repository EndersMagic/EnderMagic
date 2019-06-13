package ru.mousecray.endmagic.proxy;

import codechicken.lib.packet.PacketCustom;
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
import ru.mousecray.endmagic.init.*;
import ru.mousecray.endmagic.inventory.ContainerBlastFurnace;
import ru.mousecray.endmagic.inventory.GuiBlastFurnace;
import ru.mousecray.endmagic.network.ServerPacketHandler;
import ru.mousecray.endmagic.util.NameAndTabUtils;
import ru.mousecray.endmagic.worldgen.WorldGenEnderTrees;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class CommonProxy implements IGuiHandler {

    protected List<Item> itemsToRegister = new LinkedList<>();
    protected List<Class<? extends TileEntity>> tilesToRegister = new LinkedList<>();
    protected List<Block> blocksToRegister = new LinkedList<>();
    protected List<EntityEntry> entityToRegister = new LinkedList<>();

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        PacketCustom.assignHandler(EM.ID, new ServerPacketHandler());

        //Registration Blocks
        new ClassFieldSource<Block>(EMBlocks.class).elemes().forEach(this::registerBlock);

        //Registration Items
        new ClassFieldSource<Item>(EMItems.class).and(new ListSource<>(EMItems.createToolsAndArmor()))
                .elemes().forEach(this::registerItem);

        //Registration Entity

        entityToRegister.addAll(new ClassFieldSource<EntityEntry>(EMEntities.class).elemes());


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
        GameRegistry.registerWorldGenerator(new WorldGenEnderTrees(), 10);
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public static int blastFurnaceGui = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == blastFurnaceGui)
            return new ContainerBlastFurnace(player, EMBlocks.blockBlastFurnace.tile(world, new BlockPos(x, y, z)));

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == blastFurnaceGui)
            return new GuiBlastFurnace(new ContainerBlastFurnace(player, EMBlocks.blockBlastFurnace.tile(world, new BlockPos(x, y, z))));

        return null;
    }
}