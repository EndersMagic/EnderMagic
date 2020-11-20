package ru.mousecray.endmagic.integration.explode;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.elements.DrawableBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;

public class ExplodeCategory implements IRecipeCategory<ExplodeCraftingWrapper> {
    public static final String UID = EM.ID + ":explode";

    private final IDrawableStatic bg;

    public ExplodeCategory(IGuiHelper h) {
        bg = new DrawableBuilder(new ResourceLocation(EM.ID, "textures/gui/gui_explode.png"), 0, 0, 100, 34).setTextureSize(100, 34).build();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Explode Crafting";
    }

    @Override
    public String getModName() {
        return EM.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return bg;
    }

    @Override
    public void drawExtras(Minecraft mc) {
    }

    @Override
    public void setRecipe(IRecipeLayout layout, ExplodeCraftingWrapper recipes, IIngredients ingredients) {

        IGuiItemStackGroup isg = layout.getItemStacks();

        isg.init(0, true, 8, 8);
        isg.set(0, recipes.getInput());

        isg.init(1, false, 74, 8);
        isg.set(1, recipes.getOutput());
    }
}