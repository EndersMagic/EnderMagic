package ru.mousecray.endmagic.api.embook.pages;

import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.TextLine;

import java.util.List;
import java.util.stream.Collectors;

public class TextPage implements IPage {

    private List<IStructuralGuiElement> lines;

    public TextPage(List<String> lines) {
        this.lines = lines.stream().map(TextLine::new).collect(Collectors.toList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return lines;
    }
}
