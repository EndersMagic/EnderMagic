package ru.mousecray.endmagic.api.embook;

import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.components.IPageComponent;
import ru.mousecray.endmagic.client.gui.GuiScreenEMBook;

public abstract class PageComponent implements IPageComponent {
	
	protected GuiScreenEMBook book;
	
	protected int x;
	protected int y;
	
	protected int height;
	protected int width;
	
	protected ResourceLocation resource = new ResourceLocation(EM.ID, "textures/gui/book_elements.png");
	
	public PageComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.book = GuiScreenEMBook.instance;
	}
	
	public PageComponent setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public PageComponent setSize(int width, int height) {
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