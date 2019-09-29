package ru.mousecray.endmagic.client.gui;

import net.minecraft.client.Minecraft;
import ru.mousecray.endmagic.util.Vec2i;

public interface IStructuralGuiElement {
    default Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    default int width() {
        return 0;
    }

    default int height() {
        return 0;
    }

    default Vec2i fixPoint() {
        return new Vec2i(0, 0);
    }

    void render(int mouseX, int mouseY);
}
