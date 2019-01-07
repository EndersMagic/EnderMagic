package ru.mousecray.endmagic.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;

@SideOnly(Side.CLIENT)
public class GuiScreenEMBook extends GuiScreen {
	
	@Instance(EM.ID)
	public static GuiScreenEMBook instance;
	
    public void drawItemStack(ItemStack stack, int x, int y, String altText) {
		RenderItem itemRender = mc.getRenderItem();
		FontRenderer fontRenderer = mc.fontRenderer;
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }
    
    @Override
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
    	super.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }
    
    @Override
    public void renderToolTip(ItemStack stack, int x, int y) {
    	super.renderToolTip(stack, x, y);
    }
}