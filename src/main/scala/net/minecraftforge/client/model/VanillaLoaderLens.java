package net.minecraftforge.client.model;

public class VanillaLoaderLens {
    public static ICustomModelLoader get() {
        return ModelLoader.VanillaLoader.INSTANCE;
    }
}
