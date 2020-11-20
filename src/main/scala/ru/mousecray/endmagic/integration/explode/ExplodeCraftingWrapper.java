package ru.mousecray.endmagic.integration.explode;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ExplodeCraftingWrapper implements IRecipeWrapper {
    private final Block input;
    private final ItemStack output;

    public ExplodeCraftingWrapper(Map.Entry<Block, ItemStack> recipe) {
        input = recipe.getKey();
        output = recipe.getValue();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutput(ItemStack.class, output);
    }

    public ItemStack getInput() {
        return new ItemStack(input);
    }

    public ItemStack getOutput() {
        return output;
    }
}
