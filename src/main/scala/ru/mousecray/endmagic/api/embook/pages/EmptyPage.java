package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

import java.util.List;

public class EmptyPage implements IPage {
    @Override
    public List<IStructuralGuiElement> elements() {
        return ImmutableList.of();
    }
}
