package ru.mousecray.endmagic.api.embook.components;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.Page;

public abstract class BookChapter implements IPageSource {

    public Stream<Page> build() {
        return pages.stream().flatMap(IPageSource::build);
    }

    protected final List<IPageComponent> pages;
    public final ItemStack icon;

    public BookChapter(ItemStack icon, String chapterTitle) {
        this.icon = icon;
        pages = new ArrayList<>();
        this.chapterTitle = chapterTitle;
    }

    public BookChapter(String chapterTitle) {
        this(ItemStack.EMPTY, chapterTitle);
    }

    public final String chapterTitle;

    public List<IPageComponent> chapterComponents() {
        return pages;
    }
}