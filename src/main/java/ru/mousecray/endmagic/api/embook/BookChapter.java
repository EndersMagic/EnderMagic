package ru.mousecray.endmagic.api.embook;

import java.util.List;

import net.minecraft.item.ItemStack;

public abstract class BookChapter {
	
	private final int key;
	
	public BookChapter(int key) {
		this.key = key;
	}
	
	public abstract String getChapterTitle();
	public abstract List<IChapterComponent> getChapterComponents();
	
	public int getKey() {
		return key;
	}
	public ItemStack getChapterIcon() {
		return ItemStack.EMPTY;
	}
}