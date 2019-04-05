package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.alignment.Alignment;
import ru.mousecray.endmagic.api.embook.alignment.Min;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;

public class ItemStackView implements IStructuralGuiElement {
    public final ImmutableList<ItemStack> itemStack;
    public final Alignment x;
    public final Alignment y;
    private ItemRenderer itemRenderer = mc().getItemRenderer();

    public ItemStackView(ItemStack itemStack, Alignment x, Alignment y) {
        this.itemStack = ImmutableList.of(itemStack);
        this.x = x;
        this.y = y;
    }

    public ItemStackView(ImmutableList<ItemStack> itemStack, Alignment x, Alignment y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
    }

    public ItemStackView(ItemStack itemStack, int x, int y) {
        this(itemStack, new Min(x), new Min(y));
    }

    @Override
    public void render(int mouseX, int mouseY, int width, int height) {
        GlStateManager.translate(x.resolve(width), y.resolve(height), 0);
        itemRenderer.renderItem(mc().player, cycleItemStack(itemStack), ItemCameraTransforms.TransformType.GUI);
        GlStateManager.translate(x.resolve(width), y.resolve(height), 0);
    }

    public void drawItemStack(ItemStack stack, int x, int y, String altText) {
        RenderItem itemRender = mc().getRenderItem();
        FontRenderer fontRenderer = mc().fontRenderer;
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        itemRender.zLevel = 0.0F;
    }

    private ItemStack cycleItemStack(ImmutableList<ItemStack> itemStack) {
        if ((itemStack.size() > 0))
            return itemStack.get((int) (System.currentTimeMillis() / 1000L % itemStack.size()));
        else
            return ItemStack.EMPTY;
    }
}
