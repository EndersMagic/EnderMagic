package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.TextPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TextCompoent implements IChapterComponent {
    private final String text;
    private final static int pageSize = 10;
    private final static int lineSize = 10;
    private Map<FontRenderer, ImmutableList<IPage>> cache = new HashMap<>();

    public TextCompoent(String text) {
        this.text = text;
        pages();
    }

    private ImmutableList<IPage> buildPagesForFont(FontRenderer font) {
        String[] words = text.split("\\s+");

        ArrayList<IPage> pages = new ArrayList<>();

        ArrayList<String> lines = new ArrayList<>();

        Pair<String, Integer> lastLine = Stream.of(words).map(word -> Pair.of(word, font.getStringWidth(word)))
                .reduce(Pair.of("", 0), (s, stringIntegerPair) -> {
                    int accSize = s.getRight() + stringIntegerPair.getValue();
                    if (lineSize > accSize)
                        return Pair.of(s.getLeft() + stringIntegerPair.getLeft(), accSize);
                    else {
                        lines.add(s.getLeft());
                        return Pair.of("", 0);
                    }
                });
        lines.add(lastLine.getLeft());

        for (int i = 0; i < lines.size() / pageSize; i++) {
            pages.add(new TextPage(lines.subList(i * pageSize, Math.min(i * pageSize + pageSize, lines.size()))));
        }

        return ImmutableList.copyOf(pages);
    }

    @Override
    public List<IPage> pages() {
        return cache.computeIfAbsent(Minecraft.getMinecraft().fontRenderer, this::buildPagesForFont);
    }
}
