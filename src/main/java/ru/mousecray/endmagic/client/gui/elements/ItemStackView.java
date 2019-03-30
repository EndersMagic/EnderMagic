package ru.mousecray.endmagic.client.gui.elements;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.IStructuralGuiElement;

public class ItemStackView implements IStructuralGuiElement {
    public final ItemStack itemStack;
    public final int x, y;
    private ItemRenderer itemRenderer = mc().getItemRenderer();

    public ItemStackView(ItemStack itemStack, int x, int y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, 0);
        itemRenderer.renderItem(mc().player, itemStack, ItemCameraTransforms.TransformType.GUI);

        GlStateManager.popMatrix();
    }
}
