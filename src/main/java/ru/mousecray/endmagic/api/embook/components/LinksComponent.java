package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.LinksPage;
import ru.mousecray.endmagic.util.GroupIterator;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class LinksComponent implements IChapterComponent {
    public final static int pageSize = BookApi.pageHeight / Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    private ImmutableList<IPage> pages;

    public LinksComponent(List<String> links, String title) {
        pages = buildPagesForFont(links, title);
    }

    private ImmutableList<IPage> buildPagesForFont(List<String> links, String title) {
        GroupIterator<String> pages = new GroupIterator<>(Arrays.asList(links.toArray(new String[0])).listIterator(), pageSize, __ -> 1);

        ImmutableList.Builder<IPage> builder = ImmutableList.builder();

        builder.add(new LinksPage(pages.next(), title));

        StreamSupport.stream(Spliterators.spliteratorUnknownSize(pages, Spliterator.ORDERED), false)
                .map(LinksPage::new)
                .forEach(builder::add);

        return builder.build();
    }

    @Override
    public List<IPage> pages() {
        return pages;
    }
}
