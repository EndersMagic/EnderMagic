package ru.mousecray.endmagic.api.embook;

import net.minecraft.util.ResourceLocation;

public class ChapterComponent {
	
	private int x;
	private int y;
	
	private int height;
	private int width;
	
	private final ComponentType componentType;
	private final ResourceLocation resource;
	private int chapterKey;
	
	public ChapterComponent(ComponentType componentType, ResourceLocation resource) {
		this.componentType = componentType;
		this.resource = resource;
	}
	public ChapterComponent(ComponentType componentType, ResourceLocation resource, int x, int y, int width, int height) {
		this(componentType, resource);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public ChapterComponent setChapterLink(int key) {		
		if(componentType == ComponentType.LINK) {
			chapterKey = key;
		}
		return this;
	}
	
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
	
	public ComponentType getComponentType() {
		return componentType;
	}
	public ResourceLocation getResource() {
		return resource;
	}
}