package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.Minecraft;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

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
    public void render(int mouseX, int mouseY) {
        Minecraft.getMinecraft().fontRenderer.drawString(line, x, y, 0xaa00ff);
    }
}
