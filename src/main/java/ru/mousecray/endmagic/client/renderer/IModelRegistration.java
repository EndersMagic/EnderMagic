package ru.mousecray.endmagic.client.renderer;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;

public interface IModelRegistration {
    void addBakedModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override);
    void setModel(Block block, int meta, ModelResourceLocation resource);
    void setModel(Item item, int meta, ModelResourceLocation resource);
    void setModelVariants(Item item, ResourceLocation... variants);
    void setModelMeshDefinition(Block block, ItemMeshDefinition meshDefinition); 
    void addModelLoader(Supplier<ICustomModelLoader> modelLoader);
    void setStateMapper(Block block, IStateMapper stateMapper);
    void setTesr(Class<? extends TileEntity> tile, TileEntitySpecialRenderer tesr);
}
