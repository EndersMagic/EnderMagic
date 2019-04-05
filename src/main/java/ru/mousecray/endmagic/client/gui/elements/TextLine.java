package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.Minecraft;
import ru.mousecray.endmagic.api.embook.alignment.Alignment;
import ru.mousecray.endmagic.api.embook.alignment.Min;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

public class TextLine implements IStructuralGuiElement {
    public final String line;
    public final Alignment x;
    public final Alignment y;

    public TextLine(String line, Alignment x, Alignment y) {
        this.line = line;
        this.x = x;
        this.y = y;
    }

    public TextLine(String line, int i, int i1) {
        this(line, new Min(i), new Min(i1));
    }

    @Override
    public void render(int mouseX, int mouseY, int width, int height) {
        Minecraft.getMinecraft().fontRenderer.drawString(line, x.resolve(width), y.resolve(height), 0xaa00ff);
    }
}
