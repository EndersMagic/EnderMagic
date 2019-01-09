package ru.mousecray.endmagic.api.embook.components;

import java.util.List;

public class ChapterPage {
	
	private final List<IPageComponent> components;
	private final int key;
	
	public ChapterPage(int key, List<IPageComponent> components) {
		this.components = components;
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
	public List<IPageComponent> getComponents() {
		return components;
	}

	public void render(int mouseX, int mouseY, float partialTicks) {
		
	}
}