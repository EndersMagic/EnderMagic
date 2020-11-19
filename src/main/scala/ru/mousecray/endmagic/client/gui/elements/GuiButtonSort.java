package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.EMCreativeTab;
import ru.mousecray.endmagic.util.ResourcesUtils;

import javax.annotation.Nonnull;

public class GuiButtonSort extends GuiButton {
    protected static final ResourceLocation[] textures =
            {
                    ResourcesUtils.texture("gui/sort_button.png"),
                    ResourcesUtils.texture("gui/items_button.png"),
                    ResourcesUtils.texture("gui/blocks_button.png"),
                    ResourcesUtils.texture("gui/tools_button.png")
            };
    final GuiContainerCreative gui;
    Action action;
    int type;
    public GuiButtonSort(int id, int xPos, int yPos, GuiContainerCreative gui, int type, Action action) {
        super(id, gui.getGuiLeft() + gui.mc.fontRenderer.getStringWidth(I18n.format("em_cretive_tab")) + 6 - xPos, gui.getGuiTop() + yPos, 10, 10, "");
        this.gui = gui;
        this.action = action;
        this.type = type;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partial) {
        if (gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex())
            drawButtonMod(mc, mouseX, mouseY, partial);
    }

    @Override
    public boolean mousePressed(@Nonnull Minecraft mc, int mouseX, int mouseY) {
        boolean pressed = super.mousePressed(mc, mouseX, mouseY);
        if (pressed) {
            action.onClick();
            gui.updateCreativeSearch();
        }
        return pressed && gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex();
    }


    public void drawButtonMod(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            mc.getTextureManager().bindTexture(textures[type % 4]);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
            mouseDragged(mc, mouseX, mouseY);
        }
    }
    @FunctionalInterface
    public interface Action
    {
        void onClick();
    }
}
