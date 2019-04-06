package ru.mousecray.endmagic.client.gui;

import ru.mousecray.endmagic.api.embook.alignment.Rectangle;

public interface IClickable {
    default Rectangle area() {
        return new Rectangle(0, 0, 0, 0);
    }

    default void click() {
    }
}
