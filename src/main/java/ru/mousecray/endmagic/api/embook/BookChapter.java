package ru.mousecray.endmagic.api.embook;

import java.util.List;

import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.components.ChapterButton;

public abstract class BookChapter {
	
	protected String key;
	
	public abstract String getChapterTitle();
	public abstract List<IChapterComponent> getChapterComponents();
	
	public String getKey() {
		return key;
	}
	
	protected void setKey(String id) {
		for(IChapterComponent component : getChapterComponents()) {
			if(component.getComponentType() == ComponentType.LINK) {
				((ChapterButton)component).setChapterVisible(Integer.parseInt(id));
			}
		}
		this.key = id;
	}
	
	public ItemStack getChapterIcon() {
		return ItemStack.EMPTY;
	}
}