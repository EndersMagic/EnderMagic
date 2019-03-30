package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.api.embook.IStructuralGuiElement;

public class Tooltip extends GuiScreen implements IStructuralGuiElement {
    public final ImmutableList<String> lines;
    public final Rectangle rectangle;

    public Tooltip(ImmutableList<String> lines, Rectangle rectangle) {
        this.lines = lines;
        this.rectangle = rectangle;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (rectangle.contains(mouseX, mouseY))
            drawHoveringText(lines, mouseX, mouseY);

    }
}
