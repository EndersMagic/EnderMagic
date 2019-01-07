package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class ItemStackComponent extends ChapterComponent {
	
	private final ItemStack stack;
	private String altText = "";

	public ItemStackComponent(ItemStack stack, int x, int y) {
		super(x, y, 16, 16);
		this.stack = stack;
	}
	
    /**
     *  In GUI set zLevel from this Component
     */
	@Override
	public void render(Minecraft mc, int mouseX, int mouseY, float partialTicks) {	
		this.drawItemStack(mc, stack, x, y, altText);
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