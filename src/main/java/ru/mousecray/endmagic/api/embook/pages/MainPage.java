package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.api.embook.PageContainer;

import java.util.List;

public class MainPage implements IPage {
    public MainPage(ImmutableMap<String, PageContainer> bookContent) {
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return null;
    }
}
