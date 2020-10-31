package ru.mousecray.endmagic.integration.blast;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;

public class BlastCategory implements IRecipeCategory<BlastCraftingWrapper>
{
    public static final String UID = EM.ID + ":blast"; // Сам UID рецепт.

    private final IDrawableStatic bg; // "Background"

    public BlastCategory(IGuiHelper h)
    {
        bg = h.createDrawable(new ResourceLocation(EM.ID, "textures/gui/jei_crafter.png"), 0, 0, 100, 34); // Объявление background'а.
    }

    @Override
    public String getUid()
    { // UID рецепта.
        return UID;
    }

    @Override
    public String getTitle()
    { // Название вкладки в MC, можно использовать I18n переводчик.
        return "Blast Crafting";
    }

    @Override
    public String getModName()
    { // Название мода.
        return EM.NAME;
    }

    @Override
    public IDrawable getBackground()
    {
        return bg;
    }

    @Override
    public void drawExtras(Minecraft mc)
    { // Любые надписи, которые будут на каждом рецепте.
        // Все координаты идут относительно самого рецепта. Все width и height рассчитывать не нужно.
//        mc.fontRenderer.drawString("Block drops:", 5, 13, 0xffffffff, true);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, BlastCraftingWrapper recipes, IIngredients ingredients)
    {
        IGuiItemStackGroup isg = layout.getItemStacks(); // Группа ItemStack, которая нужна для рендера.
        // Все координаты идут относительно самого рецепта. Все width и height рассчитывать не нужно.
        isg.init(0, true, 8, 8);
        isg.set(0, recipes.getIron()); // Добавляем в слот 0 входной предмет.
        isg.init(1, true, 32, 8);
        isg.set(1, recipes.getCoal()); // Добавляем в слот 1 входной предмет.

        isg.init(2, false, 74, 8);
        isg.set(2, recipes.getOutput()); // Добавляем в слот 2 выходной предмет.
    }
}