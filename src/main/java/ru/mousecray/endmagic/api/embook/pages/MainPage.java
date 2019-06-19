package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.LinkElement;
import ru.mousecray.endmagic.client.gui.elements.TextLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MainPage implements IPage {

    private final List<IStructuralGuiElement> linkElementStream;

    public MainPage(ImmutableMap<String, PageContainer> bookContent) {
        ArrayList<Map.Entry<String, PageContainer>> chapters = new ArrayList<>(bookContent.entrySet());
        linkElementStream = Stream.concat(IntStream
                        .range(0, bookContent.size())
                        .mapToObj(i -> Pair.of(i, chapters.get(i)))
                        .map(i -> new LinkElement(
                                        I18n.format(i.getRight().getKey()), i.getRight().getValue(),
                                0, i.getLeft() * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 20
                        )),
                Stream.of(
                        new TextLine(I18n.format("book.main_page_title"),0,0)
                ))
                .collect(toList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return linkElementStream;
    }
}
