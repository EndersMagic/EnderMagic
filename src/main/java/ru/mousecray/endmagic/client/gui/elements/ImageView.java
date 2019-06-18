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
        GlStateManager.color(1,1,1,1);
        mc().getTextureManager().bindTexture(texture);
        drawModalRectWithCustomSizedTexture(rectangle.getX(), rectangle.getY(), 0, 0, rectangle.getWidth(), rectangle.getHeight(),
                atlas.getIconWidth(), atlas.getIconHeight());
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float ratio = textureWidth / textureHeight;
        int newWidth = min(width, (int) (ratio * height));
        int newHeight = min(height, (int) (1f / ratio * width));

        y -= (newHeight - height) / 2;
        x -= (newWidth - width) / 2;
        width = newWidth;
        height = newHeight;


        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + textureHeight) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + textureWidth) * f), (double) ((v + textureHeight) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + textureWidth) * f), (double) (v * f1)).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }
}
