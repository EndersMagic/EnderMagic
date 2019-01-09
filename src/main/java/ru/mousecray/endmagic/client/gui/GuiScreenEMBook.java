package ru.mousecray.endmagic.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.components.BookChapter;

@SideOnly(Side.CLIENT)
public class GuiScreenEMBook extends GuiScreen {
	
	public static GuiScreenEMBook instance = new GuiScreenEMBook();
	
	private int updateCount;
	private GuiButton buttonDone;
	private NextPageButton buttonNextPage, buttonPreviousPage;
	
	//These two variables are related
	private int currChapter = 0;
//	private int countPages = 0;
	
	
	
	private int currPage = 0;
	
	private List<BookChapter> bookContent = BookApi.getBookContent();
//	private Map<String, ChapterPage> pages = new HashMap();
	
    private static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(EM.ID, "textures/gui/book.png");
    
    @Override
    public void initGui() {
        buttonList.clear();
        
        buttonDone = addButton(new GuiButton(0, width / 2 - 100, height / 2 + 98, 200, 20, I18n.format("gui.done")));
        
        int i = (width - 256) / 2;
        int j = (height - 192) / 2;
        buttonNextPage = (NextPageButton)addButton(new NextPageButton(1, i + 215, j + 160, true));
        buttonPreviousPage = (NextPageButton)addButton(new NextPageButton(2, i + 18, j + 160, false));
        
//        for(BookChapter chapter : bookContent.values()) {
//        	for(IPageComponent component : chapter.getChapterComponents()) {
//        		if(component.getComponentType() == ComponentType.LINK) {
//        			addButton((PageButton)component);
//        		}
//        	}       	
//        	genPage(chapter, 0);
//        }
        
        updateButtons();
    }
    
//    private void genPage(BookChapter chapter, int startIndex) {
//		int guiHeight = (width - 192);
//		List<IPageComponent> components = chapter.getChapterComponents();
//		int size = components.size();
//		List<IPageComponent> currComponents = new ArrayList();
//		int currSize = 0;
//		for(int i = startIndex; i < size; i++) {
//			IPageComponent component = components.get(i);
//			if(component.getComponentType() == ComponentType.LINK) {
//				int testSize = currSize + ((PageButton)component).height;
//				if(testSize  < guiHeight) {
//					currSize = testSize;
//					currComponents.add(component);
//				}
//				else {
//					pages.put(chapter.getKey(), new ChapterPage(currComponents));
//					genPage(chapter, i);
//					break;
//				}
//			}
//			else {
//				int testSize = currSize + ((PageComponent)component).getHeight();
//				if(testSize  < guiHeight) {
//					currSize = testSize;
//					currComponents.add(component);
//				}
//				else {
//					pages.put(chapter.getKey(), new ChapterPage(currComponents));
//					genPage(chapter, i);
//					break;
//				}
//			}
//		}
//	}

	@Override
    public void updateScreen() {
        super.updateScreen();
        ++updateCount;
    }
    
    private void updateButtons() {
    	
    	//TODO: visible nextButtonPage
    	
    	buttonPreviousPage.visible = !(currChapter == 0);
    	buttonDone.visible = true;
    	
//    	for(int i = 3; i < buttonList.size(); i++) {
//    		GuiButton button = buttonList.get(i);
//    		if(currChapter == ((PageButton)button).getChapterVisible()) {
//    			button.visible = true;
//    		}
//    	}
    }
	
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BOOK_TEXTURES);
        int i = (width - 256) / 2;
        int j = (height - 192) / 2;
        drawTexturedModalRect(i, j, 0, 0, 256, 192);
        
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        
    	//TODO: Parsing chapters to page
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