package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.TextPage;
import ru.mousecray.endmagic.util.GroupIterator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TextComponent implements IChapterComponent {
    private final String text;
    private final static int pageSize = 10;
    private final static int lineSize = 10;
    private Map<FontRenderer, ImmutableList<IPage>> cache = new HashMap<>();

    public TextComponent(String text) {
        this.text = text;
        pages();
    }

    private ImmutableList<IPage> buildPagesForFont(FontRenderer font) {
        String[] words = text.split("\\s+");

        GroupIterator<String> lines = new GroupIterator<>(Arrays.asList(words).iterator(), lineSize, String::length);

        GroupIterator<List<String>> pages = new GroupIterator<>(lines, pageSize, __ -> 1);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(pages, Spliterator.ORDERED), false)
                .map(page ->
                        new TextPage(page
                                .stream()
                                .map(line -> String.join(" ", line))
                                .collect(Collectors.toList())))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<IPage> pages() {
        return cache.computeIfAbsent(Minecraft.getMinecraft().fontRenderer, this::buildPagesForFont);
    }
}
