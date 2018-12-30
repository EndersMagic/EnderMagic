package ru.mousecray.endmagic.render;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;


public interface IModelRegistration {
    void addBakedModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override);
    void setModel(Block block, int meta, ModelResourceLocation resource);
    void setModel(Item item, int meta, ModelResourceLocation resource);
}