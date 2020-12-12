package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMItems;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.ResourcesUtils;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class GuiButtonSort extends GuiButton {

    public enum SortType {
        Tools(() -> new ItemStack(phantomPickaxe)),
        Blocks(() -> new ItemStack(EMBlocks.enderLog, 1, EnderBlockTypes.EnderTreeType.PHANTOM.ordinal())),
        Items(() -> new ItemStack(EMItems.blueEnderPearl));

        final Supplier<ItemStack> itemForRender;

        SortType(Supplier<ItemStack> itemForRender) {
            this.itemForRender = itemForRender;
        }
    }

    @GameRegistry.ObjectHolder(EM.ID + ":phantom_diamond_pickaxe")
    public static Item phantomPickaxe;

    boolean isActive = true;
    final GuiContainerCreative gui;
    Action action;
    SortType type;
    String name;

    public GuiButtonSort(int id, int xPos, int yPos, GuiContainerCreative gui, SortType type, String name, Action action) {
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
            mc.getTextureManager().bindTexture(ResourcesUtils.texture("gui/creative_button.png"));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);


            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, 0);
            double scale = 0.5;
            GlStateManager.scale(scale, scale, scale);
            RenderHelper.enableStandardItemLighting();
            RenderItem itemRender = mc.getRenderItem();
            itemRender.zLevel = 90;
            itemRender.renderItemAndEffectIntoGUI(type.itemForRender.get(), 4, 3);
            itemRender.zLevel = 0;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
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
