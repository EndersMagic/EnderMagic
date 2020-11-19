package ru.mousecray.endmagic.integration.blast;

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

public class BlastCategory implements IRecipeCategory<BlastCraftingWrapper> {
    public static final String UID = EM.ID + ":blast";

    private final IDrawableStatic bg;

    public BlastCategory(IGuiHelper h) {
        bg = new DrawableBuilder(new ResourceLocation(EM.ID, "textures/gui/blast_category_2.png"), 0, 0, 100, 71).setTextureSize(100, 71).build();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Blast Crafting";
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
    public void setRecipe(IRecipeLayout layout, BlastCraftingWrapper recipes, IIngredients ingredients) {
        IGuiItemStackGroup isg = layout.getItemStacks();

        isg.init(0, true, 15, 8);
        isg.set(0, recipes.getIron());
        isg.init(1, true, 15, 44);
        isg.set(1, recipes.getCoal());

        isg.init(2, false, 68, 27);
        isg.set(2, recipes.getOutput());
    }
}