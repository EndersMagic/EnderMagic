package ru.mousecray.endmagic.api.embook.pages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.LinkElement;
import ru.mousecray.endmagic.client.gui.elements.TextLine;

import java.util.ArrayList;
import java.util.List;

public class LinksPage implements IPage {

    private final List<IStructuralGuiElement> linkElementStream;

    public LinksPage(List<String> links) {
        this(links, "");
    }

    public LinksPage(List<String> links, String title) {
        linkElementStream = new ArrayList<>();

        for (int i = 0; i < links.size(); i++) {
            String link = links.get(i);
            PageContainer chapter = BookApi.getBookContent().get(link);
            linkElementStream.add(new LinkElement(I18n.format(link), chapter, 0, i * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 20));
        }

        linkElementStream.add(new TextLine(I18n.format(title), 0, 0));
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return linkElementStream;
    }
}
