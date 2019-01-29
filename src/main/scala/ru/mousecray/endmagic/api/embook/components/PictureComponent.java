package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class PictureComponent extends ChapterComponent {
	
	public PictureComponent(ResourceLocation resource, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.resource = resource;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.PICTURE;
	}
}