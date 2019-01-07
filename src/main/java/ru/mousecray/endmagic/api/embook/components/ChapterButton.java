package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.api.embook.ComponentType;
import ru.mousecray.endmagic.api.embook.IChapterComponent;

@SideOnly(Side.CLIENT) 
public class ChapterButton extends GuiButton implements IChapterComponent {
	
	private final String text;
	private int chapterVisible;
	
	public ChapterButton(int button, int x, int y, String text) {
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
	
	public void setChapterVisible(int chapterVisible) {
		this.chapterVisible = chapterVisible;
	}
	
	public int getChapterVisible() {
		return chapterVisible;
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.LINK;
	}
}