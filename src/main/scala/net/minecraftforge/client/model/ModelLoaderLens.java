package net.minecraftforge.client.model;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ModelLoaderLens {
    public static ModelLoader get() {
        return ReflectionHelper.getPrivateValue(ModelLoader.VanillaLoader.class, ModelLoader.VanillaLoader.INSTANCE, "loader");
    }
}
