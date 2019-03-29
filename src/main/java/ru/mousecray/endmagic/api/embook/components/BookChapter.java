package ru.mousecray.endmagic.api.embook.components;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public abstract class BookChapter {
	
	protected final List<ChapterPage> pages;
	protected int key;
	
	public BookChapter() {
		pages = new ArrayList<ChapterPage>();
	}
	
	public abstract String getChapterTitle();
	public List<ChapterPage> getChapterComponents() {
		return pages;
	}
	
	public int getKey() {
		return key;
	}
	
	protected void setKey(int id) {
//		for(IPageComponent component : getChapterComponents()) {
//			if(component.getComponentType() == ComponentType.LINK) {
//				((PageButton)component).setChapterVisible(Integer.parseInt(id));
//			}
//		}
//		this.key = id;
	}
	
	public ItemStack getChapterIcon() {
		return ItemStack.EMPTY;
	}
	
	public void render(int startX, int startY, int mouseX, int mouseY, float partialTicks) {
		for(ChapterPage component : getChapterComponents()) {
			component.render(mouseX, mouseY, partialTicks);
		}
	}
}