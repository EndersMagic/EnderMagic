package ru.mousecray.endmagic.api.embook;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ChapterComponent {
	
	protected int x;
	protected int y;
	
	private int height;
	private int width;
	
	protected ResourceLocation resource;
	protected int chapterKey;
	
	public ChapterComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@SideOnly(Side.CLIENT) public abstract void render(Minecraft mc);
	
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