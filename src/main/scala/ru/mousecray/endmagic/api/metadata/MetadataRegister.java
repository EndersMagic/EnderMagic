package ru.mousecray.endmagic.api.metadata;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MetadataRegister<T extends Block & IMetadataBlock> {

    private final List<T> blocksToRegister = new ArrayList<>();
    private final List<MetadataContainer.MetaItemBlock> itemsToRegister = new ArrayList<>();
    private final List<Class<? extends TileEntity>> tilesToRegister = new ArrayList<>();

    protected MetadataRegister() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Nonnull
    public static MetadataRegister create() {
        return new MetadataRegister();
    }

    public void addBlockToRegister(@Nonnull T block) {
        List<Class<? extends TileEntity>> tiles = new ArrayList<>();
        block.getMetadataContainer().getValidStates().forEach(
                state -> tiles.add(((MetadataContainer.ExtendedStateImpl) state).createTileEntity(null).getClass()));
        tiles.forEach(tile -> { if (tile != null) tilesToRegister.add(tile); });
        blocksToRegister.add(block);
        MetadataContainer.MetaItemBlock itemBlock = block.getItemBlock(block);
        itemsToRegister.add(itemBlock);
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent e) {
        blocksToRegister.forEach(block -> block.getMetadataContainer().registerItemModels(block));
    }

    @SubscribeEvent
    public void onBlockRegister(@Nonnull RegistryEvent.Register<Block> e) {
        blocksToRegister.forEach(e.getRegistry()::register);
        tilesToRegister.forEach(tile -> GameRegistry.registerTileEntity(tile,
                new ResourceLocation(Loader.instance().activeModContainer().getModId(), tile.getSimpleName())));
    }

    @SubscribeEvent
    public void onItemRegister(@Nonnull RegistryEvent.Register<Item> e) {
        itemsToRegister.forEach(e.getRegistry()::register);
    }
}