package ru.mousecray.endmagic.api.embook;

import net.minecraft.client.Minecraft;

public interface IStructuralGuiElement {
    default Minecraft mc(){return Minecraft.getMinecraft()}

    void render(int mouseX, int mouseY);
}
