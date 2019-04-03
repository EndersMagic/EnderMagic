package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

public class ImageView extends GuiScreen implements IStructuralGuiElement {
    public final ResourceLocation texture;
    public final Rectangle rectangle;

    public ImageView(ResourceLocation texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        mc().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(rectangle.getX(), rectangle.getY(), 0, 0, rectangle.getWidth(), rectangle.getHeight());
    }
}
