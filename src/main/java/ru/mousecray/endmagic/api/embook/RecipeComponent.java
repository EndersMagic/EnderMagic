package ru.mousecray.endmagic.api.embook;

public class RecipeComponent extends PageComponent {

	public RecipeComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// You can see the code here? I do not see it)
		// Give variant from FuelRecipeComponent
	}

	@Override
	public ComponentType getComponentType() {
		return ComponentType.RECIPE;
	}
}