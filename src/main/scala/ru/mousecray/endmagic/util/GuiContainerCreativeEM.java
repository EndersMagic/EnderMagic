package ru.mousecray.endmagic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import ru.mousecray.endmagic.client.gui.elements.EMTextField;
import ru.mousecray.endmagic.client.gui.elements.GuiButtonSort;

import java.io.IOException;

public class GuiContainerCreativeEM extends GuiContainerCreative {
    public GuiContainerCreativeEM(EntityPlayer player) {
        super(player);
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButtonSort(15, 8, 4, 10, 10, "", this));
        searchField = new EMTextField(searchField.getId(), Minecraft.getMinecraft().fontRenderer, searchField.x, searchField.y, searchField.width, searchField.height, this);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        searchField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void setCurrentCreativeTab(CreativeTabs tab) {
        super.setCurrentCreativeTab(tab);
        searchField.width = tab.getSearchbarWidth() - 2;
    }
}