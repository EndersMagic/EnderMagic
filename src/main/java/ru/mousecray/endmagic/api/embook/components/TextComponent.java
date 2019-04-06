package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.TextPage;
import ru.mousecray.endmagic.util.GroupIterator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TextComponent implements IChapterComponent {
    private final String text;
    public final static int pageSize = (BookApi.pageHeight - 35) / Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    public final static int lineSize = BookApi.pageWidth - 30;
    private Map<FontRenderer, ImmutableList<IPage>> cache = new HashMap<>();
    private ImmutableList<IPage> pages;

    public TextComponent(String text) {
        this.text = text;
        pages();
        pages = buildPagesForFont(Minecraft.getMinecraft().fontRenderer);
    }

    private ImmutableList<IPage> buildPagesForFont(FontRenderer font) {
        font.setUnicodeFlag(true);
        String[] words = text.split("\\s+");

        GroupIterator<String> lines = new GroupIterator<>(Arrays.asList(words).listIterator(), lineSize, w -> font.getStringWidth(w) + 1);

        GroupIterator<List<String>> pages = new GroupIterator<>(lines, pageSize, __ -> 1);
        Iterator<List<List<String>>> pages1 = pages;

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(pages1, Spliterator.ORDERED), false)
                .map(page ->
                        new TextPage(page
                                .stream()
                                .map(line -> String.join(" ", line))
                                .collect(Collectors.toList())))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<IPage> pages() {
        return pages;
    }
}
