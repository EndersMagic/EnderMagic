package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.EMCreativeTab;
import ru.mousecray.endmagic.util.ResourcesUtils;

import javax.annotation.Nonnull;

public class GuiButtonSort extends GuiButton
{
    protected static final ResourceLocation[] textures =
            {
                    ResourcesUtils.texture(  "gui/sort_button.png"),
                    ResourcesUtils.texture( "gui/items_button.png"),
                    ResourcesUtils.texture("gui/blocks_button.png"),
                    ResourcesUtils.texture( "gui/tools_button.png")
            };
    final GuiContainerCreative gui;

    public GuiButtonSort(int id, int xPos, int yPos, int width, int height, String displayString, GuiContainerCreative gui)
    {
        super(id, gui.getGuiLeft() + gui.getXSize() - width - xPos, gui.getGuiTop() + yPos, width, height, displayString);
        this.gui = gui;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partial)
    {
        if(gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex())
            drawButtonMod(mc, mouseX, mouseY, partial);
    }

    @Override
    public boolean mousePressed(@Nonnull Minecraft mc, int mouseX, int mouseY)
    {
        boolean pressed = super.mousePressed(mc, mouseX, mouseY);
        if (pressed)
        {
            EMCreativeTab.Sort = (EMCreativeTab.Sort + 1) % 4;
            gui.updateCreativeSearch();
        }
        return pressed && gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex();
    }


    public void drawButtonMod(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(textures[EMCreativeTab.Sort % 4]);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, width, height, width, height);
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}
