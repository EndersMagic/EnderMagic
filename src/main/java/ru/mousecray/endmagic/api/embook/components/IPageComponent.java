package ru.mousecray.endmagic.api.embook.components;

import ru.mousecray.endmagic.api.embook.ComponentType;

/*
 * Don't use this interface
 */
public interface IPageComponent extends IPageSource {
    public ComponentType getComponentType();

    public void render(int mouseX, int mouseY, float partialTicks);
}
