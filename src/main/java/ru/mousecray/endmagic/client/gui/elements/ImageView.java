package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

import java.io.IOException;

import static java.lang.Math.min;

public class ImageView extends GuiScreen implements IStructuralGuiElement {
    public final ResourceLocation texture;
    public final Rectangle rectangle;
    private int textureWidth;
    private int textureHeight;

    public ImageView(ResourceLocation texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
        try {
            PngSizeInfo pngsizeinfo = PngSizeInfo.makeFromResource(mc().getResourceManager().getResource(texture));
            textureWidth = pngsizeinfo.pngWidth;
            textureHeight = pngsizeinfo.pngHeight;
        } catch (IOException e) {
            textureWidth = textureHeight = 16;
            e.printStackTrace();
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc().getTextureManager().bindTexture(texture);
        drawInscribedCustomSizeModalRect(rectangle.getX(), rectangle.getY(),
                rectangle.getWidth(), rectangle.getHeight(),
                textureWidth, textureHeight);
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
