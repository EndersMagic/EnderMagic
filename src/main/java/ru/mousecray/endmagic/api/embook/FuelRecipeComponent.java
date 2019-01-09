package ru.mousecray.endmagic.api.embook;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class FuelRecipeComponent extends PageComponent {
	
	private final ItemStack fuelStack;
	private final ItemStack smeltingStack;
	private final ItemStack readyStack;
	
	public FuelRecipeComponent(ItemStack fuelStack, ItemStack smaltingStack, ItemStack readyStack, int x, int y) {
		super(x, y, 100, 100);
		this.fuelStack = fuelStack;
		this.smeltingStack = smaltingStack;
		this.readyStack = readyStack;
	}

    /**
     *  In GUI set zLevel from this Component
     */
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        book.mc.getTextureManager().bindTexture(resource);
        //TODO: Create Texture and set this coordinates
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 76, width, height, 275, 250);
        boolean flag = mouseX >= x + 6 && mouseY >= y + 5 && mouseX < x + 22 && mouseY < y + 20;
        boolean flag2 = mouseX >= x + 6 && mouseY >= y + 40 && mouseX < x + 22 && mouseY < y + 56;
        boolean flag3 = mouseX >= x + 70 && mouseY >= y + 22 && mouseX < x + 86 && mouseY < y + 38;
		if(flag) book.drawGradientRect(x + 6, y + 5, x + 22, y + 20, -2130706433, -2130706433);
		if(flag2) book.drawGradientRect(x + 6, y + 40, x + 22, y + 56, -2130706433, -2130706433);
        if(flag3) book.drawGradientRect(x + 69, y + 21, x + 87, y + 39, -2130706433, -2130706433);

        book.drawItemStack(smeltingStack, x + 5, y + 4, (String)null);
		book.drawItemStack(fuelStack, x + 6, y + 40, (String)null);
		book.drawItemStack(readyStack, x + 70, y + 22, (String)null);
		
        if(flag) book.renderToolTip(smeltingStack, mouseX, mouseY);
        if(flag2) book.renderToolTip(readyStack, mouseX, mouseY);
        if(flag3) book.renderToolTip(fuelStack, mouseX, mouseY);
	}
	
	public ItemStack getFuelStack() {
		return fuelStack;
	}
	
	public ItemStack getSmeltingStack() {
		return smeltingStack;
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.RECIPE;
	}
}