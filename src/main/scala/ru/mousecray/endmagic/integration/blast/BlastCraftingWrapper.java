package ru.mousecray.endmagic.integration.blast;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.blocks.BlockBlastFurnace;

public class BlastCraftingWrapper implements IRecipeWrapper {
    private final Item coal, iron, steel;

    public BlastCraftingWrapper(BlockBlastFurnace.BlastRecipe recipe) {
        coal = recipe.coal;
        iron = recipe.iron;
        steel = recipe.steel;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, ImmutableList.of(getIron(), getCoal()));
        ingredients.setOutput(ItemStack.class, getOutput());
    }

    public ItemStack getIron() {
        return new ItemStack(iron);
    }

    public ItemStack getCoal() {
        return new ItemStack(coal);
    }

    public ItemStack getOutput() {
        return new ItemStack(steel);
    }
}