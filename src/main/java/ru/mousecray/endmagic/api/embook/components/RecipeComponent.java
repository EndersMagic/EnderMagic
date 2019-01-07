package ru.mousecray.endmagic.api.embook.components;

import net.minecraft.client.Minecraft;
import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class RecipeComponent extends ChapterComponent {

	public RecipeComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.RECIPE;
	}
}