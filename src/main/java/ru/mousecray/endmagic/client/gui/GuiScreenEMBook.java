package ru.mousecray.endmagic.client.gui;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.blocks.ListBlock;
import ru.mousecray.endmagic.items.ListItem;

@SideOnly(Side.CLIENT)
public class GuiScreenEMBook extends GuiScreen {
    private static final ResourceLocation 
    BOOK_TEXTURES = new ResourceLocation(EndMagicData.ID, "textures/gui/book.png"),
    BOOK_ELEMENTS = new ResourceLocation(EndMagicData.ID, "textures/gui/book_elements.png"),
    BOOK_ELEMENTS2 = new ResourceLocation(EndMagicData.ID, "textures/gui/book_elements2.png"),
    BOOK_ELEMENTS3 = new ResourceLocation(EndMagicData.ID, "textures/gui/book_elements3.png");
    
    private int updateCount;
    private int chapter0Pages = 1, chapter1Pages = 2, chapter2Pages = 2, chapter3Pages = 1, chapter4Pages = 1;
    private int currPage = 0;
    private NextPageButton buttonNextPage, buttonPreviousPage;
    private GuiButton buttonDone;
    private SelectChapterButton button0, button1, button2, button3, button4, button5;
    private int chapter = 0;
    private int step = 0;
    private List<ITextComponent> cachedComponents;
    private int revert = 0;
    private boolean flagRevert = false;
    
    @Override
    public void initGui() {
        buttonList.clear();
        
        buttonDone = addButton(new GuiButton(0, width / 2 - 100, height / 2 + 98, 200, 20, I18n.format("gui.done")));
        
        int i = (width - 256) / 2;
        int j = (height - 192) / 2;
        buttonNextPage = (NextPageButton)addButton(new NextPageButton(1, i + 215, j + 160, true));
        buttonPreviousPage = (NextPageButton)addButton(new NextPageButton(2, i + 18, j + 160, false));
        button0 = (SelectChapterButton)addButton(new SelectChapterButton(3, i + 30, j + 40, "Static Portal"));
        button1 = (SelectChapterButton)addButton(new SelectChapterButton(4, i + 30, j + 50, "Dark Portal"));
        button2 = (SelectChapterButton)addButton(new SelectChapterButton(5, i + 30, j + 60, "Items"));
        button3 = (SelectChapterButton)addButton(new SelectChapterButton(6, i + 30, j + 70, "Smelting Recipes"));
        button4 = (SelectChapterButton)addButton(new SelectChapterButton(7, i + 30, j + 60, "Building"));
        button5 = (SelectChapterButton)addButton(new SelectChapterButton(8, i + 30, j + 70, "Activation"));
        
        updateButtons();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++updateCount;
    }
    
    private void updateButtons() {
    	switch (chapter) {
			case 0:
		        buttonNextPage.visible = (currPage < chapter0Pages - 1);
				break;
			case 1:
		        buttonNextPage.visible = (currPage < chapter1Pages - 1) && step == 2;
				break;
			case 2:
		        buttonNextPage.visible = (currPage < chapter2Pages - 1) && step == 2;
				break;
			case 3:
		        buttonNextPage.visible = (currPage < chapter3Pages - 1);
				break;
			case 4:
		        buttonNextPage.visible = (currPage < chapter4Pages - 1);
				break;
		}
        buttonPreviousPage.visible = !(chapter == 0);
        buttonDone.visible = true;
        button0.visible = (chapter == 0);
        button1.visible = (chapter == 0);
        button2.visible = (chapter == 0);
        button3.visible = (chapter == 0);
        button3.visible = (chapter == 0);
        button4.visible = (chapter == 1) || (chapter == 2);
        button5.visible = (chapter == 1) || (chapter == 2);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if(button.id == 0) {
            	mc.displayGuiScreen((GuiScreen)null);
            }
            
            else if(button.id == 1) {
            	if (chapter == 0 && currPage < chapter0Pages - 1) ++currPage;
            	else if (chapter == 1 && currPage < chapter1Pages - 1) ++currPage;
            	else if (chapter == 2 && currPage < chapter2Pages - 1) ++currPage;
            	else if (chapter == 3 && currPage < chapter3Pages - 1) ++currPage;
            	else if (chapter == 4 && currPage < chapter4Pages - 1) ++currPage;
            }
            else if(button.id == 2) {
            	if (currPage > 0) --currPage;
            	else chapter = 0;
            }
            else if(button.id == 3) chapter = 1;
            else if(button.id == 4) chapter = 2;
            else if(button.id == 5) chapter = 3;
            else if(button.id == 6) chapter = 4;
            else if(button.id == 7) {
            	step = 1;
            	currPage = 0;
            }
            else if(button.id == 8) step = 2;

            this.updateButtons();
        }
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
        
        if(chapter == 0) {
        	String s1 = TextFormatting.DARK_PURPLE + "Table of Contents";
        	fontRenderer.drawString(s1, i + 5 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
        	fontRenderer.drawString(TextFormatting.DARK_GRAY + "Chapters:", i + 20, j + 30, 0);
        }
        else if(chapter == 1) {
        	if(currPage == 0 || currPage == 1) {
        		String s1 = TextFormatting.DARK_PURPLE + "Static Portal";
        		fontRenderer.drawString(s1, i + 5 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
        		ITextComponent textF = new TextComponentString("Static teleport teleports only one way, to the Bone "
        				+ "Block, with which portal is connected. To charge it, use a bucket of water on a static "
        				+ "generator. " + TextFormatting.RED + "For teleportation, the Bone Block should stand upright.");
                cachedComponents = textF != null ? GuiUtilRenderComponents.splitText(textF, 110, fontRenderer, true, true) : null;
                int k1S = Math.min(110 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
                
                int frS = 0;
                for (int l1 = 0; l1 < k1S; ++l1) {
                    ITextComponent textF2 = cachedComponents.get(l1);
                    fontRenderer.drawString(textF2.getUnformattedText(), i + 20, j + 30 + l1 * fontRenderer.FONT_HEIGHT, 0);
                    if(!(l1 + 1 < k1S)) frS = j + 30 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
                }
                fontRenderer.drawString(TextFormatting.DARK_GRAY + "Steps:", i + 20, (int)(frS*1.02), 0);
                button4.y = (int)(frS*1.09);
                button5.y = (int)(frS*1.09 + 10);
		        if(step == 1) {
		        	String s2 = TextFormatting.DARK_PURPLE + "Building Static Portal";
		        	fontRenderer.drawString(s2, i + 45 + (256 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
		        	String s3 = "Pattern";
		        	fontRenderer.drawString(s3, i + 5 + (384 - fontRenderer.getStringWidth(s1)) / 2, j + 35, 0);
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            mc.getTextureManager().bindTexture(BOOK_ELEMENTS2);
		            drawModalRectWithCustomSizedTexture(i + 154, j + 55, 0, 0, 65, 108, 174, 160);
		        	if((mouseX >= i + 154 && mouseY >= j + 55 && mouseX < i + 176 && mouseY < j + 75) || 
		        		(mouseX >= i + 198 && mouseY >= j + 55 && mouseX < i + 220 && mouseY < j + 75) ||
		        		(mouseX >= i + 154 && mouseY >= j + 115 && mouseX < i + 176 && mouseY < j + 135) ||
		        		(mouseX >= i + 198 && mouseY >= j + 115 && mouseX < i + 220 && mouseY < j + 135)) {
		        			drawHoveringText(Blocks.STONEBRICK.getLocalizedName(), mouseX, mouseY);
		        	}
		        	if((mouseX >= i + 154 && mouseY >= j + 75 && mouseX < i + 176 && mouseY < j + 95) || 
		        		(mouseX >= i + 198 && mouseY >= j + 75 && mouseX < i + 220 && mouseY < j + 95) ||
		        		(mouseX >= i + 154 && mouseY >= j + 95 && mouseX < i + 176 && mouseY < j + 115) ||
		        		(mouseX >= i + 198 && mouseY >= j + 95 && mouseX < i + 220 && mouseY < j + 115)) {
			        		drawHoveringText(Blocks.COBBLESTONE_WALL.getLocalizedName(), mouseX, mouseY);
			        	}
		        	if(mouseX >= i + 176 && mouseY >= j + 55 && mouseX < i + 198 && mouseY < j + 75) {
			        	drawHoveringText(ListBlock.GEN.getLocalizedName(), mouseX, mouseY);
			        }
		        	if(mouseX >= i + 176 && mouseY >= j + 115 && mouseX < i + 198 && mouseY < j + 135) {
		        		drawHoveringText(ListBlock.TP.getLocalizedName(), mouseX, mouseY);
		        	}
		        	if(mouseX >= i + 183 && mouseY >= j + 82 && mouseX < i + 191 && mouseY < j + 88) {
			        	drawHoveringText(Blocks.STONE_BUTTON.getLocalizedName(), mouseX, mouseY);
			        }
		        	if(mouseX >= i + 176 && mouseY >= j + 135 && mouseX < i + 198 && mouseY < j + 155) {
			        	drawHoveringText(Blocks.REDSTONE_BLOCK.getLocalizedName(), mouseX, mouseY);
			        }
		        }
		        else if(currPage == 0 && step == 2) {
		        	String s2 = TextFormatting.DARK_PURPLE + "Activation Static Portal";
		        	fontRenderer.drawString(s2, i + 100 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
		        	String s3 = TextFormatting.DARK_PURPLE + "1/2";
		        	fontRenderer.drawString(s3, i + (384 - fontRenderer.getStringWidth(s3)) / 2, j + 25, 0);
	        		ITextComponent textT = new TextComponentString("First of all put the block of bone in the destination and use the static portal activator on it.");
	                cachedComponents = textT != null ? GuiUtilRenderComponents.splitText(textT, 110, fontRenderer, true, true) : null;
	                int k1T = Math.min(110 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
	                
	                int frT = 0;
	                for (int l1 = 0; l1 < k1T; ++l1) {
	                    ITextComponent textT2 = cachedComponents.get(l1);
	                    fontRenderer.drawString(textT2.getUnformattedText(), i + 137, j + 35 + l1 * fontRenderer.FONT_HEIGHT, 0);
	                    if(!(l1 + 1 < k1T)) frT = j + 35 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
	                }
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            mc.getTextureManager().bindTexture(BOOK_ELEMENTS3);
		            drawModalRectWithCustomSizedTexture(i + 155, (int)(frT*1.02), 0, 0, 65, 70, 130, 140);
		        }
		        else if(currPage == 1 && step == 2) {
		        	String s2 = TextFormatting.DARK_PURPLE + "Activation Static Portal";
		        	fontRenderer.drawString(s2, i + 100 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
		        	String s3 = TextFormatting.DARK_PURPLE + "2/2";
		        	fontRenderer.drawString(s3, i + (384 - fontRenderer.getStringWidth(s3)) / 2, j + 25, 0);
	        		ITextComponent textT = new TextComponentString("Now use the static portal activator on portal block.");
	                cachedComponents = textT != null ? GuiUtilRenderComponents.splitText(textT, 110, fontRenderer, true, true) : null;
	                int k1T = Math.min(110 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
	                
	                int frT = 0;
	                for (int l1 = 0; l1 < k1T; ++l1) {
	                    ITextComponent textT2 = cachedComponents.get(l1);
	                    fontRenderer.drawString(textT2.getUnformattedText(), i + 137, j + 35 + l1 * fontRenderer.FONT_HEIGHT, 0);
	                    if(!(l1 + 1 < k1T)) frT = j + 35 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
	                }
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            mc.getTextureManager().bindTexture(BOOK_ELEMENTS3);
		            drawModalRectWithCustomSizedTexture(i + 155, (int)(frT*1.02), 210, 0, 70, 70, 140, 140);
		        }
        	}
        }
        else if(chapter == 2) {
        	if(currPage == 0 || currPage == 1) {
        		String s1 = TextFormatting.DARK_PURPLE + "Dark Portal";
        		fontRenderer.drawString(s1, i + 5 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
        		ITextComponent textF = new TextComponentString("Dark portal, requires more resources to create, but two dark portals can be linked. To charge it, use a bucket of lava on a dark generator.");
                cachedComponents = textF != null ? GuiUtilRenderComponents.splitText(textF, 110, fontRenderer, true, true) : null;
                int k1S = Math.min(110 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
                
                int frS = 0;
                for (int l1 = 0; l1 < k1S; ++l1) {
                    ITextComponent textF2 = cachedComponents.get(l1);
                    fontRenderer.drawString(textF2.getUnformattedText(), i + 20, j + 30 + l1 * fontRenderer.FONT_HEIGHT, 0);
                    if(!(l1 + 1 < k1S)) frS = j + 30 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
                }
                fontRenderer.drawString(TextFormatting.DARK_GRAY + "Steps:", i + 20, (int)(frS*1.02), 0);
                button4.y = (int)(frS*1.09);
                button5.y = (int)(frS*1.09 + 10);
		        if(step == 1) {
		        	String s2 = TextFormatting.DARK_PURPLE + "Building Dark Portal";
		        	fontRenderer.drawString(s2, i + 45 + (256 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
		        	String s3 = "Pattern";
		        	fontRenderer.drawString(s3, i + 5 + (384 - fontRenderer.getStringWidth(s1)) / 2, j + 35, 0);
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            mc.getTextureManager().bindTexture(BOOK_ELEMENTS2);
		            drawModalRectWithCustomSizedTexture(i + 154, j + 55, 66, 0, 65, 108, 174, 160);
		        	if((mouseX >= i + 154 && mouseY >= j + 55 && mouseX < i + 176 && mouseY < j + 75) || 
		        		(mouseX >= i + 198 && mouseY >= j + 55 && mouseX < i + 220 && mouseY < j + 75) ||
		        		(mouseX >= i + 154 && mouseY >= j + 115 && mouseX < i + 176 && mouseY < j + 135) ||
		        		(mouseX >= i + 198 && mouseY >= j + 115 && mouseX < i + 220 && mouseY < j + 135)) {
		        			drawHoveringText(Blocks.STONEBRICK.getLocalizedName(), mouseX, mouseY);
		        	}
		        	if((mouseX >= i + 154 && mouseY >= j + 75 && mouseX < i + 176 && mouseY < j + 95) || 
		        		(mouseX >= i + 198 && mouseY >= j + 75 && mouseX < i + 220 && mouseY < j + 95) ||
		        		(mouseX >= i + 154 && mouseY >= j + 95 && mouseX < i + 176 && mouseY < j + 115) ||
		        		(mouseX >= i + 198 && mouseY >= j + 95 && mouseX < i + 220 && mouseY < j + 115)) {
			        		drawHoveringText(Blocks.COBBLESTONE_WALL.getLocalizedName(), mouseX, mouseY);
			        	}
		        	if(mouseX >= i + 176 && mouseY >= j + 55 && mouseX < i + 198 && mouseY < j + 75) {
			        	drawHoveringText(ListBlock.GEN_N.getLocalizedName(), mouseX, mouseY);
			        }
		        	if(mouseX >= i + 176 && mouseY >= j + 115 && mouseX < i + 198 && mouseY < j + 135) {
		        		drawHoveringText(ListBlock.TPH.getLocalizedName(), mouseX, mouseY);
		        	}
		        	if(mouseX >= i + 183 && mouseY >= j + 82 && mouseX < i + 191 && mouseY < j + 88) {
			        	drawHoveringText(Blocks.STONE_BUTTON.getLocalizedName(), mouseX, mouseY);
			        }
		        	if(mouseX >= i + 176 && mouseY >= j + 135 && mouseX < i + 198 && mouseY < j + 155) {
			        	drawHoveringText(Blocks.REDSTONE_BLOCK.getLocalizedName(), mouseX, mouseY);
			        }
		        }
		        else if(currPage == 0 && step == 2) {
		        	String s2 = TextFormatting.DARK_PURPLE + "Activation Dark Portal";
		        	fontRenderer.drawString(s2, i + 100 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
		        	String s3 = TextFormatting.DARK_PURPLE + "1/2";
		        	fontRenderer.drawString(s3, i + (384 - fontRenderer.getStringWidth(s3)) / 2, j + 25, 0);
	        		ITextComponent textT = new TextComponentString("First of all use the dark portal activator on portal block.");
	                cachedComponents = textT != null ? GuiUtilRenderComponents.splitText(textT, 110, fontRenderer, true, true) : null;
	                int k1T = Math.min(110 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
	                
	                int frT = 0;
	                for (int l1 = 0; l1 < k1T; ++l1) {
	                    ITextComponent textT2 = cachedComponents.get(l1);
	                    fontRenderer.drawString(textT2.getUnformattedText(), i + 137, j + 35 + l1 * fontRenderer.FONT_HEIGHT, 0);
	                    if(!(l1 + 1 < k1T)) frT = j + 35 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
	                }
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            mc.getTextureManager().bindTexture(BOOK_ELEMENTS3);
		            drawModalRectWithCustomSizedTexture(i + 155, (int)(frT*1.02), 0, 70, 65, 70, 130, 140);
		        }
		        else if(currPage == 1 && step == 2) {
		        	String s2 = TextFormatting.DARK_PURPLE + "Activation Dark Portal";
		        	fontRenderer.drawString(s2, i + 100 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
		        	String s3 = TextFormatting.DARK_PURPLE + "2/2";
		        	fontRenderer.drawString(s3, i + (384 - fontRenderer.getStringWidth(s3)) / 2, j + 25, 0);
	        		ITextComponent textT = new TextComponentString("Now use the dark portal activator on another portal block.");
	                cachedComponents = textT != null ? GuiUtilRenderComponents.splitText(textT, 110, fontRenderer, true, true) : null;
	                int k1T = Math.min(110 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
	                
	                int frT = 0;
	                for (int l1 = 0; l1 < k1T; ++l1) {
	                    ITextComponent textT2 = cachedComponents.get(l1);
	                    fontRenderer.drawString(textT2.getUnformattedText(), i + 137, j + 35 + l1 * fontRenderer.FONT_HEIGHT, 0);
	                    if(!(l1 + 1 < k1T)) frT = j + 35 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
	                }
                	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		            mc.getTextureManager().bindTexture(BOOK_ELEMENTS3);
		            drawModalRectWithCustomSizedTexture(i + 155, (int)(frT*1.02), 210, 70, 70, 70, 140, 140);
		        }
        	}
        }
        else if(chapter == 3) {
        	if(currPage == 0) {
        		String s1 = TextFormatting.DARK_PURPLE + "Items";
        		fontRenderer.drawString(s1, i + 5 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
        		ItemStack stack = new ItemStack(ListItem.ENDER_ARROW);
        		ItemStack stack2 = new ItemStack(ListItem.PURPLE_PEARL);
                GlStateManager.enableDepth();
        		drawItemStack(stack, i + 20, j + 30, /*TextFormatting.YELLOW.toString() + Math.min(stack.getMaxStackSize(), 64)*/ (String)null);
        		ITextComponent textF = new TextComponentString("This arrow has a small amount of damage, but it can kill " + TextFormatting.LIGHT_PURPLE + "endermans!");
                cachedComponents = textF != null ? GuiUtilRenderComponents.splitText(textF, 80, fontRenderer, true, true) : null;
                int k1S = Math.min(80 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
                
                int frS = 0;
                for (int l1 = 0; l1 < k1S; ++l1) {
                    ITextComponent textF2 = cachedComponents.get(l1);
                    fontRenderer.drawString(textF2.getUnformattedText(), i + 46, j + 30 + l1 * fontRenderer.FONT_HEIGHT, 0);
                    if(!(l1 + 1 < k1S)) frS = j + 30 + (l1 + 1) * fontRenderer.FONT_HEIGHT;
                }
        		if(mouseX >= i + 20 && mouseY >= j + 30 && mouseX < i + 36 && mouseY < j + 46) {
        			drawHoveringText(ListItem.ENDER_ARROW.getItemStackDisplayName(stack), mouseX, mouseY);
        		}
        		drawItemStack(stack2, i + 20, (int)(frS*1.05), /*TextFormatting.YELLOW.toString() + Math.min(stack.getMaxStackSize(), 64)*/ (String)null);
        		ITextComponent textT = new TextComponentString("This pearl teleports any creature to you, provided that it has reached it.");
                cachedComponents = textT != null ? GuiUtilRenderComponents.splitText(textT, 80, fontRenderer, true, true) : null;
                int k1T = Math.min(80 / fontRenderer.FONT_HEIGHT, cachedComponents.size());
                
                for (int l1 = 0; l1 < k1T; ++l1) {
                    ITextComponent textT2 = cachedComponents.get(l1);
                    fontRenderer.drawString(textT2.getUnformattedText(), i + 46, (int)(frS*1.05) + l1 * fontRenderer.FONT_HEIGHT, 0);
                }
        		if(mouseX >= i + 20 && mouseY >= (int)(frS*1.05) && mouseX < i + 36 && mouseY < (int)(frS*1.05 + 16)) {
        			drawHoveringText(ListItem.PURPLE_PEARL.getItemStackDisplayName(stack), mouseX, mouseY);
        		}
        	}
        }
        else if(chapter == 4) {
        	if(currPage == 0) {
        		String s1 = TextFormatting.DARK_PURPLE + "Smelting Recipes";
        		fontRenderer.drawString(s1, i + 5 + (128 - fontRenderer.getStringWidth(s1)) / 2, j + 15, 0);
        		drawFuelWidgetI(i + 20, j + 25, 0, mouseX, mouseY, partialTicks, new ItemStack(Items.ENDER_PEARL), new ItemStack(ListItem.ENDERITE_BRICK));
        		if (updateCount / 10 % 2 == 0) {
        			if(!flagRevert) {
	                	if(revert <= 3) {revert++;}
	                	else revert = 0;
	                	flagRevert = true;
        			}
                }
        		else if(flagRevert){
                	flagRevert = false;
        		}
        		drawFuelWidgetB(i + 140, j + 25, 0, mouseX, mouseY, partialTicks, //EM: Dom't indent below
                new ItemStack(ListBlock.ENDER_LOGS, 1, revert == 0 ? 0 : revert == 1 ? 1 : revert == 2 ? 2 : 3), new ItemStack(ListItem.ENDER_COALS, 1, revert == 0 ? 0 : revert == 1 ? 1 : revert == 2 ? 2 : 3));
        		drawFuelWidgetB(i + 20, j + 90, 0, mouseX, mouseY, partialTicks, new ItemStack(ListBlock.ROUGH_ENDERITE), new ItemStack(ListBlock.TECH_ENDERITE, 1, 0));
                boolean flag3 = mouseX >= i + 90 && mouseY >= j + 37 && mouseX < i + 106 && mouseY < j + 63;
                if(flag3) renderToolTip(new ItemStack(ListItem.ENDERITE_BRICK), mouseX, mouseY);
        	}
        }
    }
    
    private void drawItemStack(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
    
    private void drawFuelWidgetI(int i, int j, int count, int mouseX, int mouseY, float ticks, ItemStack stack1, ItemStack stack2) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getTextureManager().bindTexture(BOOK_ELEMENTS);
        drawModalRectWithCustomSizedTexture(i, j, 0, 76, 100, 100, 275, 250);
        boolean flag = mouseX >= i + 6 && mouseY >= j + 5 && mouseX < i + 22 && mouseY < j + 20;
        boolean flag2 = mouseX >= i + 6 && mouseY >= j + 40 && mouseX < i + 22 && mouseY < j + 56;
        boolean flag3 = mouseX >= i + 70 && mouseY >= j + 22 && mouseX < i + 86 && mouseY < j + 38;
		if(flag) drawGradientRect(i + 6, j + 5, i + 22, j + 20, -2130706433, -2130706433);
		if(flag2) drawGradientRect(i + 6, j + 40, i + 22, j + 56, -2130706433, -2130706433);
        if(flag3) drawGradientRect(i + 69, j + 21, i + 87, j + 39, -2130706433, -2130706433);
        drawItemStack(stack1, i + 5, j + 4, (String)null);
        ItemStack stack3 = new ItemStack(Items.COAL);
		drawItemStack(stack3, i + 6, j + 40, (String)null);
		drawItemStack(stack2, i + 70, j + 22, (String)null);
        if(flag) renderToolTip(stack1, mouseX, mouseY);
        if(flag2) renderToolTip(stack3, mouseX, mouseY);
    }
    
    private void drawFuelWidgetB(int i, int j, int count, int mouseX, int mouseY, float ticks, ItemStack stack1, ItemStack stack2) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getTextureManager().bindTexture(BOOK_ELEMENTS);
        drawModalRectWithCustomSizedTexture(i, j, 0, 76, 100, 100, 275, 250);
        boolean flag = mouseX >= i + 6 && mouseY >= j + 5 && mouseX < i + 22 && mouseY < j + 20;
        boolean flag2 = mouseX >= i + 6 && mouseY >= j + 40 && mouseX < i + 22 && mouseY < j + 56;
        boolean flag3 = mouseX >= i + 70 && mouseY >= j + 22 && mouseX < i + 86 && mouseY < j + 38;
		if(flag) drawGradientRect(i + 6, j + 5, i + 22, j + 20, -2130706433, -2130706433);
		if(flag2) drawGradientRect(i + 6, j + 40, i + 22, j + 56, -2130706433, -2130706433);
        if(flag3) drawGradientRect(i + 69, j + 21, i + 87, j + 39, -2130706433, -2130706433);
        drawItemStack(stack1, i + 6, j + 5, (String)null);
        ItemStack stack3 = new ItemStack(Items.COAL);
		drawItemStack(stack3, i + 6, j + 40, (String)null);
		drawItemStack(stack2, i + 70, j + 22, (String)null);
        if(flag) renderToolTip(stack1, mouseX, mouseY);
        if(flag2) renderToolTip(stack3, mouseX, mouseY);
		if(flag3) renderToolTip(stack2, mouseX, mouseY);
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
    			mc.getTextureManager().bindTexture(GuiScreenEMBook.BOOK_TEXTURES);
    			int i = 0, j = 192;
    			if (flag) i += 23;
    			if (!isForward) j += 13;
    			drawTexturedModalRect(x, y, i, j, 23, 13);
    		}
    	}
    }
    
    @SideOnly(Side.CLIENT)
    static class SelectChapterButton extends GuiButton {
    	private final String text;
    	
    	public SelectChapterButton(int button, int x, int y, String text) {
    		super(button, x, y, 95, 8, "");
    		this.text = text;
        }
    	
    	@Override
    	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    		if (visible) {
    			boolean flag = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    			mc.fontRenderer.drawString(flag ? TextFormatting.GREEN + text : TextFormatting.BLUE + text, x, y, 0);
    		}
    	}
    }
}