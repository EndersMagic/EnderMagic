package net.minecraftforge.client.model;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ModelLoaderAccessor {
    public static ModelLoader get() {
        return ReflectionHelper.getPrivateValue(ModelLoader.VanillaLoader.class, ModelLoader.VanillaLoader.INSTANCE, "loader");
    }
}
