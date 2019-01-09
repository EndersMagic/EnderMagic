package ru.mousecray.endmagic.api.embook;

public class TextComponent extends PageComponent {

	public TextComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.TEXT;
	}
}