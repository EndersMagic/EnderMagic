package ru.mousecray.endmagic.api.embook;

import net.minecraft.util.ResourceLocation;

public class PictureComponent extends PageComponent {
	
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