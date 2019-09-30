package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
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
    public int width() {
        return mc().fontRenderer.getStringWidth(line);
    }

    @Override
    public int height() {
        return mc().fontRenderer.FONT_HEIGHT;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        mc().fontRenderer.drawString(line, x, y, 0xaa00ff);
    }
}
