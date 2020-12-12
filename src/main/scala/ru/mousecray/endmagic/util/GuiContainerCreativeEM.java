package ru.mousecray.endmagic.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import ru.mousecray.endmagic.client.gui.elements.EMTextField;
import ru.mousecray.endmagic.client.gui.elements.GuiButtonSort;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GuiContainerCreativeEM extends GuiContainerCreative {
    public GuiContainerCreativeEM(EntityPlayer player) {
        super(player);
    }

    @Override
    public void initGui() {
        super.initGui();
        searchField = new EMTextField(searchField.getId(), Minecraft.getMinecraft().fontRenderer, searchField.x, searchField.y, searchField.width - 38, searchField.height, this);
        buttonList.add(new GuiButtonSort(15, getGuiLeft() + 77,  4,this, 0, "gui.creative.button.tools",  () -> EMCreativeTab.tools  = !EMCreativeTab.tools));
        buttonList.add(new GuiButtonSort(16, getGuiLeft() + 90,  4,this, 1, "gui.creative.button.blocks", () -> EMCreativeTab.blocks = !EMCreativeTab.blocks));
        buttonList.add(new GuiButtonSort(17, getGuiLeft() + 103, 4,this, 2, "gui.creative.button.items",  () -> EMCreativeTab.items  = !EMCreativeTab.items));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        searchField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void renderHoveredToolTip(int x, int y)
    {
        super.renderHoveredToolTip(x, y);
        buttonList.stream().filter((bnt) -> bnt instanceof GuiButtonSort).forEach((bnt) -> ((GuiButtonSort) bnt).drawButtonTip(x, y));
    }
}