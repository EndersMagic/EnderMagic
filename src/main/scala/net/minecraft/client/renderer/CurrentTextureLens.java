package net.minecraft.client.renderer;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CurrentTextureLens {
    public static int get() {
        GlStateManager.TextureState[] textureState = ReflectionHelper.<GlStateManager.TextureState[], GlStateManager>getPrivateValue(GlStateManager.class, null, "textureState");
        Integer activeTextureUnit = ReflectionHelper.<Integer, GlStateManager>getPrivateValue(GlStateManager.class, null, "activeTextureUnit");
        return textureState[activeTextureUnit].textureName;
    }
}
