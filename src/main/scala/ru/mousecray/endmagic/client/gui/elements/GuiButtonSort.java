package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.ResourcesUtils;

import javax.annotation.Nonnull;

public class GuiButtonSort extends GuiButton {
    protected static final ResourceLocation[] textures =
            {
                    ResourcesUtils.texture("gui/tools_button.png"),
                    ResourcesUtils.texture("gui/blocks_button.png"),
                    ResourcesUtils.texture("gui/items_button.png"),
                    ResourcesUtils.texture("gui/tools_button_dis.png"),
                    ResourcesUtils.texture("gui/blocks_button_dis.png"),
                    ResourcesUtils.texture("gui/items_button_dis.png")
            };

    boolean isActive = true;
    final GuiContainerCreative gui;
    Action action;
    int type;
    String name;

    public GuiButtonSort(int id, int xPos, int yPos, GuiContainerCreative gui, int type, String name, Action action) {
        super(id, xPos, gui.getGuiTop() + yPos, 12, 12, "");
        this.gui = gui;
        this.action = action;
        this.type = type;
        this.name = I18n.format(name);
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partial) {
        if (gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex())
            drawButtonMod(mc, mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(@Nonnull Minecraft mc, int mouseX, int mouseY) {
        boolean pressed = super.mousePressed(mc, mouseX, mouseY);
        if (pressed) {
            action.onClick();
            isActive = !isActive;
            gui.updateCreativeSearch();
        }
        return pressed && gui.getSelectedTabIndex() == EM.EM_CREATIVE.getTabIndex();
    }


    public void drawButtonMod(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

            mc.getTextureManager().bindTexture(ResourcesUtils.texture("gui/creative_button.png"));

            GlStateManager.color(1, 1, 1, 1);
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);

            if (hovered) {
                GlStateManager.color(1, 0, 1, 0.4f);
                drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
                GlStateManager.color(1, 1, 1, 1);
            }

            mc.getTextureManager().bindTexture(textures[type + (isActive ? 0 : 3)]);
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        }
    }

    public void drawButtonTip(int mouseX, int mouseY) {
        if (hovered) {
            FontRenderer font = Minecraft.getMinecraft().fontRenderer;
            int nameWidth = font.getStringWidth(name) / 2 + 4;
            GlStateManager.translate(40, mouseY, 1);
            GuiUtils.drawHoveringText(ImmutableList.of(name), mouseX + nameWidth, mouseY, width, height, -1, font);
            GlStateManager.translate(-40, -mouseY, -1);
        }
    }

    @FunctionalInterface
    public interface Action {
        void onClick();
    }
}
