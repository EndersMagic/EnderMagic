package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.EM;

public class EMTextField extends GuiTextField
{
    final GuiContainerCreative gui;

    int realX;
    public EMTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int width, int height, GuiContainerCreative gui)
    {
        super(componentId, fontrendererObj, x, y, width, height);
        setCanLoseFocus(true);
        setFocused(false);
        setTextColor(16777215);
        realX = x;
        this.gui = gui;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.setFocused(mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height);
        return isFocused();
    }

    @Override
    public void setFocused(boolean isFocusedIn) {

    }

    @Override
    public void drawTextBox()
    {
        if(CreativeTabs.CREATIVE_TAB_ARRAY[gui.getSelectedTabIndex()].hasSearchBar())
        {
            setEnableBackgroundDrawing(false);
            super.drawTextBox();
        }
        /* God forgive me for this code, but I do not know where the position changes and where to cancel it. */
        x = gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex() ? realX + 36 : realX;
    }
}
