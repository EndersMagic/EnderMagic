package ru.mousecray.endmagic.integration.explode;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class ExplodeCraftingWrapper implements IRecipeWrapper
{

    private ItemStack input; // Объявление входного предмета для рецепта.
    private ItemStack output; // Объявление выходного листа для рецепта.

    public ExplodeCraftingWrapper(ExplodeRecipe recipe) { // Сам рецепт. Так же можно передавать сюда свои рецепты и разбирать его на входные/выходные предметы.
        input = recipe.getInput(); // Собственно сам входной предмет.
        output = recipe.getOutput(); // Собственно сам выходной предмет.
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input); // Входные ингридиенты.
        ingredients.setOutput(ItemStack.class, output); // Выходные ингридиенты.
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
