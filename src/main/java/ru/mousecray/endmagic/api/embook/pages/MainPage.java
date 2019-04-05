package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.api.embook.alignment.Min;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.LinkElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MainPage implements IPage {

    private final List<IStructuralGuiElement> linkElementStream;

    public MainPage(ImmutableMap<String, PageContainer> bookContent) {
        ArrayList<Map.Entry<String, PageContainer>> chapters = new ArrayList<>(bookContent.entrySet());
        linkElementStream = IntStream
                .range(0, bookContent.size())
                .mapToObj(i -> Pair.of(i, chapters.get(i)))
                .map(i -> new LinkElement(
                        i.getRight().getKey(), i.getRight().getValue(),
                        new Min(0), new Min(i.getLeft() * 20)
                )).collect(toList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return linkElementStream;
    }
}
