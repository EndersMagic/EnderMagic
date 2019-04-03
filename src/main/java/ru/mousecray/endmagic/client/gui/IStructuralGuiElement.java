package ru.mousecray.endmagic.client.gui;

import net.minecraft.client.Minecraft;

public interface IStructuralGuiElement {
    default Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    void render(int mouseX, int mouseY);
}
