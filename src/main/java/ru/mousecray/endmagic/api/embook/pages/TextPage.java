package ru.mousecray.endmagic.api.embook.pages;

import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.TextLine;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.mousecray.endmagic.api.embook.alignment.Alignment.left;

public class TextPage implements IPage {

    private List<IStructuralGuiElement> lines;

    public TextPage(List<String> lines) {
        this.lines = IntStream.range(0, lines.size())
                .mapToObj(i -> Pair.of(i, lines.get(i)))
                .map(line ->
                        new TextLine(line.getRight(),
                                20,
                                line.getLeft() * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 10)
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return lines;
    }
}
