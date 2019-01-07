package ru.mousecray.endmagic.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;

@SideOnly(Side.CLIENT)
public class GuiScreenEMBook extends GuiScreen {
	
    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");
	
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
    
    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton {
    	private final boolean isForward;

    	public NextPageButton(int button, int x, int y, boolean isForward) {
    		super(button, x, y, 23, 13, "");
    		this.isForward = isForward;
        }
    	
    	@Override
    	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    		if (visible) {
    			boolean flag = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    			mc.getTextureManager().bindTexture(BOOK_TEXTURES);
    			int i = 0, j = 192;
    			if (flag) i += 23;
    			if (!isForward) j += 13;
    			drawTexturedModalRect(x, y, i, j, 23, 13);
    		}
    	}
    }
}