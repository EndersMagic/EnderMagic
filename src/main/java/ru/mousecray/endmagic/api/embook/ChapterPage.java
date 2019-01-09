package ru.mousecray.endmagic.api.embook;

import java.util.List;

/**
 * Don't use this class
 */
public class ChapterPage {
	
	private final List<IChapterComponent> components;
	
	public ChapterPage(List<IChapterComponent> components) {
		this.components = components;
	}
	
	public List<IChapterComponent> getComponents() {
		return components;
	}
}