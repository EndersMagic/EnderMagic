package ru.mousecray.endmagic.client.gui.elements;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.IStructuralGuiElement;

public class ItemStackView implements IStructuralGuiElement {
    public final ImmutableList<ItemStack> itemStack;
    public final int x, y;
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
        GlStateManager.translate(x, y, 0);
        itemRenderer.renderItem(mc().player, cycleItemStack(itemStack), ItemCameraTransforms.TransformType.GUI);
        GlStateManager.translate(-x, -y, 0);
    }

    private ItemStack cycleItemStack(ImmutableList<ItemStack> itemStack) {
        if ((itemStack.size() > 0))
            return itemStack.get((int) (System.currentTimeMillis() / 1000L % itemStack.size()));
        else
            return ItemStack.EMPTY;
    }
}
