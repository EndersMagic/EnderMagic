package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class FuelRecipeComponent extends ChapterComponent {
	
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
	public void render(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getTextureManager().bindTexture(resource);
        //TODO: Create Texture and set this coordinates
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 76, width, height, 275, 250);
        boolean flag = mouseX >= x + 6 && mouseY >= y + 5 && mouseX < x + 22 && mouseY < y + 20;
        boolean flag2 = mouseX >= x + 6 && mouseY >= y + 40 && mouseX < x + 22 && mouseY < y + 56;
        boolean flag3 = mouseX >= x + 70 && mouseY >= y + 22 && mouseX < x + 86 && mouseY < y + 38;
		if(flag) this.drawGradientRect(x + 6, y + 5, x + 22, y + 20, -2130706433, -2130706433);
		if(flag2) this.drawGradientRect(x + 6, y + 40, x + 22, y + 56, -2130706433, -2130706433);
        if(flag3) this.drawGradientRect(x + 69, y + 21, x + 87, y + 39, -2130706433, -2130706433);
        this.drawItemStack(mc, smeltingStack, x + 5, y + 4, (String)null);
		this.drawItemStack(mc, fuelStack, x + 6, y + 40, (String)null);
		this.drawItemStack(mc, readyStack, x + 70, y + 22, (String)null);
//        if(flag) renderToolTip(smeltingStack, mouseX, mouseY);
//        if(flag2) renderToolTip(readyStack, mouseX, mouseY);
//        if(flag3) renderToolTip(fuelStack, mouseX, mouseY);
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