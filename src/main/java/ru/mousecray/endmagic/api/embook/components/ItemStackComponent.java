package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class ItemStackComponent extends ChapterComponent {
	
	private final ItemStack stack;
	private String altText = "";

	public ItemStackComponent(ItemStack stack, int x, int y) {
		super(x, y, 16, 16);
		this.stack = stack;
	}

	@Override
	@SideOnly(Side.CLIENT) 
	public void render(Minecraft mc) {	
		//TODO: In GUI set zLevel to 200
		RenderItem itemRender = mc.getRenderItem();
		FontRenderer fontRenderer = mc.fontRenderer;
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
//        zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
//        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
	}
	
	public void setAltText(String altText) {
		this.altText = altText;
	}
	
	public String getAltText() {
		return altText;
	}
	
	public ItemStack getStack() {
		return stack;
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.ITEM_STACK;
	}
}