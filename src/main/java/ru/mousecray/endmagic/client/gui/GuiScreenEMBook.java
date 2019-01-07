package ru.mousecray.endmagic.client.gui;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.BookChapter;
import ru.mousecray.endmagic.api.embook.ComponentType;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.components.ChapterButton;

@SideOnly(Side.CLIENT)
public class GuiScreenEMBook extends GuiScreen {
	
	public static GuiScreenEMBook instance = new GuiScreenEMBook();
	
	private int updateCount;
	private GuiButton buttonDone;
	private NextPageButton buttonNextPage, buttonPreviousPage;
	private int chapter = 0;
	private int currPage = 0;
	
	private static Map<String, BookChapter> bookContent = BookApi.getBookContent();
	
    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");
    
    @Override
    public void initGui() {
        buttonList.clear();
        
        buttonDone = addButton(new GuiButton(0, width / 2 - 100, height / 2 + 98, 200, 20, I18n.format("gui.done")));
        
        int i = (width - 256) / 2;
        int j = (height - 192) / 2;
        buttonNextPage = (NextPageButton)addButton(new NextPageButton(1, i + 215, j + 160, true));
        buttonPreviousPage = (NextPageButton)addButton(new NextPageButton(2, i + 18, j + 160, false));
        
        for(BookChapter chapter : bookContent.values()) {
        	for(IChapterComponent component : chapter.getChapterComponents()) {
        		if(component.getComponentType() == ComponentType.LINK) {
        			addButton((ChapterButton)component);
        		}
        	}
        }
        
        updateButtons();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++updateCount;
    }
    
    private void updateButtons() {
    	
    	//TODO: visible nextButtonPage
    	
    	buttonPreviousPage.visible = !(chapter == 0);
    	buttonDone.visible = true;
    	
    	for(int i = 3; i < buttonList.size(); i++) {
    		GuiButton button = buttonList.get(i);
    		if(chapter == ((ChapterButton)button).getChapterVisible()) {
    			button.visible = true;
    		}
    	}
    }
	
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BOOK_TEXTURES);
        int i = (width - 256) / 2;
        int j = (height - 192) / 2;
        drawTexturedModalRect(i, j, 0, 0, 256, 192);
        
    	for(BookChapter chapter : bookContent.values()) {
    		chapter.render(i, j, mouseX, mouseY, partialTicks);
    	}
    }
    
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