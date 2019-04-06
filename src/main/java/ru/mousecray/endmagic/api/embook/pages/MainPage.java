package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.LinkElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static ru.mousecray.endmagic.api.embook.alignment.Alignment.top;

public class MainPage implements IPage {

    private final List<IStructuralGuiElement> linkElementStream;

    public MainPage(ImmutableMap<String, PageContainer> bookContent) {
        ArrayList<Map.Entry<String, PageContainer>> chapters = new ArrayList<>(bookContent.entrySet());
        linkElementStream = IntStream
                .range(0, bookContent.size())
                .mapToObj(i -> Pair.of(i, chapters.get(i)))
                .map(i -> new LinkElement(
                        i.getRight().getKey(), i.getRight().getValue(),
                        20,i.getLeft() * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 20
                )).collect(toList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return linkElementStream;
    }
}
