package ru.mousecray.endmagic.init;

import java.util.function.Supplier;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CustomModelLoader implements ICustomModelLoader {
    private ResourceLocation modelLocation;
    private Supplier<IModel> model;

    public CustomModelLoader(ResourceLocation modelLocation, Supplier<IModel> model) {
        this.modelLocation = modelLocation;
        this.model = model;
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return this.modelLocation.equals(modelLocation);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
//        return new ModelBlock(new ResourceLocation(/*Путь до текстуры блока*/"blocks/enchanting_table_top"));
    	return model.get();
    }

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}
}