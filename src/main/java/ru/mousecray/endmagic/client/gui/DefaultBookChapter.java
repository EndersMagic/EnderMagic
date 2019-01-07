package ru.mousecray.endmagic.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import ru.mousecray.endmagic.api.embook.BookChapter;
import ru.mousecray.endmagic.api.embook.IChapterComponent;

public class DefaultBookChapter extends BookChapter {
	
	private List<IChapterComponent> components;

	public DefaultBookChapter(int key) {
		super(key);
		components = new ArrayList<IChapterComponent>();
	}

	@Override
	public String getChapterTitle() {
		return I18n.format("embook.chapter_title." + getKey());
	}
	
	public DefaultBookChapter build(IChapterComponent... elements) {
		for(IChapterComponent component : elements) {
			components.add(component);
		}
		return this;
	}

	@Override
	public List<IChapterComponent> getChapterComponents() {		
		return components;
	}
}
