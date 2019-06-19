package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.Rectangle;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

public class ItemStackView extends GuiScreen implements IStructuralGuiElement {
    public final ImmutableList<ItemStack> itemStack;
    public final int x;
    public final int y;
    private ItemRenderer itemRenderer = mc().getItemRenderer();
    private final Rectangle itemArea;

    public ItemStackView(ItemStack itemStack, int x, int y) {
        this(ImmutableList.of(itemStack), x, y);
    }

    public ItemStackView(ImmutableList<ItemStack> itemStack, int x, int y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
        itemArea = new Rectangle(this.x, this.y, this.x + 16, this.y + 16);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        ItemStack stack = cycleItemStack(itemStack);
        drawItemStack(stack, x, y, "");
        if (itemArea.contains(mouseX, mouseY))
            renderToolTip(stack, mouseX, mouseY);
    }

    public void drawItemStack(ItemStack stack, int x, int y, String altText) {
        RenderItem itemRender = mc().getRenderItem();
        FontRenderer fontRenderer = mc().fontRenderer;
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.zLevel = 0.0F;
    }

    private ItemStack cycleItemStack(ImmutableList<ItemStack> itemStack) {
        if ((itemStack.size() > 0))
            return itemStack.get((int) (System.currentTimeMillis() / 1000L % itemStack.size()));
        else
            return ItemStack.EMPTY;
    }
}
