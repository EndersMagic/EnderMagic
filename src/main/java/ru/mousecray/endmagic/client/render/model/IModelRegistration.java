package ru.mousecray.endmagic.client.render.model;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;


public interface IModelRegistration {
    void registerTexture(ResourceLocation resourceLocation);

    void addBakedModelOverride(ModelResourceLocation resource, Function<IBakedModel, IBakedModel> override);

    default void addBakedModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override) {
        addBakedModelOverride(new ModelResourceLocation(resource.getResourceDomain(), resource.getResourcePath()), override);
    }

    void setModel(Block block, int meta, ModelResourceLocation resource);

    void setModel(Item item, int meta, ModelResourceLocation resource);

    void setStateMapper(Block block, IStateMapper stateMapper);
}
