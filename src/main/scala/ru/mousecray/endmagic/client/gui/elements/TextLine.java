package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

import java.awt.*;
import java.util.Random;

public class TextLine implements IStructuralGuiElement {
    public final String line;
    public final int x;
    public final int y;

    public TextLine(String line, int x, int y) {
        this.line = line;
        this.x = x;
        this.y = y;
    }

    @Override
    public int width() {
        return mc().fontRenderer.getStringWidth(line);
    }

    @Override
    public int height() {
        return mc().fontRenderer.FONT_HEIGHT;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GlStateManager.disableTexture2D();
        Random random = new Random();
        //drawRect(x,y,x+width(),y+height(),new RGBA(random.nextInt(255),random.nextInt(255),random.nextInt(255)).argb());
        GlStateManager.enableTexture2D();
        mc().fontRenderer.drawString(line, x, y, 0x82237f);
    }

    public static void drawRect(int left, int top, int right, int bottom, int color)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)left, (double)bottom, 0.0D).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0D).endVertex();
        bufferbuilder.pos((double)right, (double)top, 0.0D).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
