package ru.mousecray.endmagic.util;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class GuiContainerCreativeEM extends GuiContainerCreative
{
    public GuiContainerCreativeEM(EntityPlayer player)
    {
        super(player);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
