package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.EM;

public class EMTextField extends GuiTextField {
    final GuiContainerCreative gui;

    int realX;

    public EMTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int width, int height, GuiContainerCreative gui) {
        super(componentId, fontrendererObj, x, y, width, height);
        setCanLoseFocus(gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex());
        System.out.println("focused " + (gui.getSelectedTabIndex() != EM.EM_CREATIVE.getTabIndex()));
        super.setFocused(gui.getSelectedTabIndex() != EM.EM_CREATIVE.getTabIndex());
        setTextColor(16777215);
        realX = x;
        this.gui = gui;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.setFocused(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height);
        return isFocused();
    }

    @Override
    public void setFocused(boolean isFocusedIn) {
        if (gui.getSelectedTabIndex() != EM.EM_CREATIVE.getTabIndex())
            super.setFocused(isFocusedIn);
    }

    @Override
    public void drawTextBox() {
        if (CreativeTabs.CREATIVE_TAB_ARRAY[gui.getSelectedTabIndex()].hasSearchBar()) {
            setEnableBackgroundDrawing(false);
            super.drawTextBox();
        }
        /* God forgive me for this code, but I do not know where the position changes and where to cancel it. */
        x = gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex() ? realX + 36 : realX;
    }
}
