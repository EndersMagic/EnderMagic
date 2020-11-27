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
import ru.mousecray.endmagic.util.EMCreativeTab;
import ru.mousecray.endmagic.util.ResourcesUtils;

import javax.annotation.Nonnull;
import java.util.List;

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
        this.name = name;
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
        if (visible){
            mc.getTextureManager().bindTexture(textures[type + (isActive ? 0 : 3)]);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
            mouseDragged(mc, mouseX, mouseY);
            if (hovered)
                GuiUtils.drawHoveringText(ImmutableList.of(getName()), mouseX, mouseY, this.width, this.height, -1, Minecraft.getMinecraft().fontRenderer);

        }
    }

    @FunctionalInterface
    public interface Action
    {
        void onClick();
    }

    public String getName()
    {
        return name;
    }
}
