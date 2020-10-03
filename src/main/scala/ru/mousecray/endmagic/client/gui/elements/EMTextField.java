package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.EM;

public class EMTextField extends GuiTextField
{
    final GuiContainerCreative gui;

    public EMTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int width, int height, GuiContainerCreative gui)
    {
        super(componentId, fontrendererObj, x, y, width, height);
        setCanLoseFocus(true);
        setFocused(false);
        setTextColor(16777215);
        this.gui = gui;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        System.out.println(mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height);
        setFocused(mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height);
        return mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }

    @Override
    public void drawTextBox()
    {
        if(CreativeTabs.CREATIVE_TAB_ARRAY[gui.getSelectedTabIndex()].hasSearchBar())
        {
            setEnableBackgroundDrawing(gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex());
            super.drawTextBox();
        }
    }
}
