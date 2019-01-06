package ru.mousecray.endmagic.api.embook.components;

import ru.mousecray.endmagic.api.embook.ChapterComponent;
import ru.mousecray.endmagic.api.embook.ComponentType;

public class RecipeComponent extends ChapterComponent {

	public RecipeComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public ChapterComponent buildComponent() {	
		return null;
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.RECIPE;
	}
}