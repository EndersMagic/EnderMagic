package ru.mousecray.endmagic.integration.explode;

import javafx.util.Pair;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.util.registry.ExplodeRecipe;

import java.util.Map;

public class ExplodeCraftingWrapper implements IRecipeWrapper
{
    private final Block input; // Объявление входного предмета для рецепта.
    private final ItemStack output; // Объявление выходного листа для рецепта.

    public ExplodeCraftingWrapper(Map.Entry<Block, ItemStack> recipe) { // Сам рецепт. Так же можно передавать сюда свои рецепты и разбирать его на входные/выходные предметы.
        input = recipe.getKey(); // Собственно сам входной предмет.
        output = recipe.getValue(); // Собственно сам выходной предмет.
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input); // Входные ингридиенты.
        ingredients.setOutput(ItemStack.class, output); // Выходные ингридиенты.
    }

    public ItemStack getInput() {
        return new ItemStack(input);
    }

    public ItemStack getOutput() {
        return output;
    }
}
