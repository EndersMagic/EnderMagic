package ru.mousecray.endmagic.api.embook;

import net.minecraft.util.ResourceLocation;

public abstract class ChapterComponent {
	
	private int x;
	private int y;
	
	private int height;
	private int width;
	
	private ResourceLocation resource;
	private int chapterKey;
	
	public ChapterComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract ChapterComponent buildComponent();
	
	public abstract ComponentType getComponentType();
	
	public ChapterComponent setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public ChapterComponent setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ResourceLocation getResource() {
		return resource;
	}
}