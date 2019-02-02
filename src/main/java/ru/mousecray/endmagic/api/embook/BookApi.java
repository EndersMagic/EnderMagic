package ru.mousecray.endmagic.api.embook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.embook.components.BookChapter;
import ru.mousecray.endmagic.api.embook.components.IPageSource;

@SideOnly(Side.CLIENT)
public class BookApi implements IPageSource {
    public static BookApi instance = new BookApi();
    public List<BookChapter> book = new ArrayList<>();

    @Override
    public Stream<Page> build() {
        return book.stream().flatMap(BookChapter::build);
    }
}