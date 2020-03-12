package ru.mousecray.endmagic.client.gui.elements;

import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.api.embook.Rectangle;
import ru.mousecray.endmagic.client.gui.GuiScreenEMBook;
import ru.mousecray.endmagic.client.gui.IClickable;

public class LinkElement extends TextLine implements IClickable {
    public final PageContainer page;

    public LinkElement(String name, PageContainer page, int x, int y) {
        super(name, x, y);
        this.page = page;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
    }

    @Override
    public Rectangle area() {
        return new Rectangle(x, y, x + mc().fontRenderer.getStringWidth(line), y + mc().fontRenderer.FONT_HEIGHT);
    }

    @Override
    public void click() {
        GuiScreenEMBook.instance.setCurrentPage(page);
    }
}
