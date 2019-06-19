package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

public class ItemStackView implements IStructuralGuiElement {
    public final ImmutableList<ItemStack> itemStack;
    public final int x;
    public final int y;
    private ItemRenderer itemRenderer = mc().getItemRenderer();

    public ItemStackView(ItemStack itemStack, int x, int y) {
        this.itemStack = ImmutableList.of(itemStack);
        this.x = x;
        this.y = y;
    }

    public ItemStackView(ImmutableList<ItemStack> itemStack, int x, int y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        ItemStack stack = cycleItemStack(itemStack);
        drawItemStack(stack,x,y,"");
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
