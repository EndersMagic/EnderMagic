package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

import static java.lang.Math.min;

public class ImageView extends GuiScreen implements IStructuralGuiElement {
    public final ResourceLocation texture;
    public final Rectangle rectangle;
    private final TextureAtlasSprite atlas;

    public ImageView(ResourceLocation texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
        atlas = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc().getTextureManager().bindTexture(texture);
        drawInscribedCustomSizeModalRect(rectangle.getX(), rectangle.getY(),
                rectangle.getWidth(), rectangle.getHeight(),
                atlas.getIconWidth(), atlas.getIconHeight());
    }

    public static void drawInscribedCustomSizeModalRect(int x, int y, int width, int height, float textureWidth, float textureHeight) {
        float ratio = textureWidth / textureHeight;
        int newWidth = min(width, (int) (ratio * height));
        int newHeight = min(height, (int) (1f / ratio * width));

        int ny = y + (height - newHeight) / 2;
        int nx = x + (width - newWidth) / 2;

        drawScaledCustomSizeModalRect(nx, ny, 0, 0,
                1, 1,
                newWidth, newHeight,
                1, 1);
    }
}
