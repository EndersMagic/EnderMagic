package ru.mousecray.endmagic.util.registry;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMItems;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ExplodeRecipe {
    private static List<ExplodeRecipe> recipes = new ArrayList<>(); // Лист всех рецептов.
    public static ExplodeRecipe EMPTY = new ExplodeRecipe(is(Items.AIR), is(Items.AIR));

    public static List<ExplodeRecipe> getRecipes() {
        return recipes;
    }

    private final ItemStack input, output;

    public ExplodeRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public static ExplodeRecipe addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output) {
        ExplodeRecipe recipe = new ExplodeRecipe(input, output);
        recipes.add(recipe);
        return recipe;
    }

    public static ExplodeRecipe getRecipe(ItemStack is) {
        if (is == null || is.isEmpty())
            return EMPTY;
        for (ExplodeRecipe recipe : recipes)
            if (recipe.matchesInput(is))
                return recipe;
        return EMPTY;
    }

    public static ExplodeRecipe getRecipe(Block block) {
        if (block == null)
            return EMPTY;
        for (ExplodeRecipe recipe : recipes)
            if (recipe.getInput().getItem() == new ItemStack(block).getItem())
                return recipe;
        return EMPTY;
    }

    public boolean matchesInput(ItemStack is) {
        return is.getItem() == input.getItem();
    }

    public boolean matchesInput(Block block) {
        return Item.getItemFromBlock(block) == input.getItem();
    }

    public static void initRecipes() { // Метод регистрации рецептов.
        addRecipe(is(EMBlocks.dragonCoal), is(EMItems.dragonDiamond));
        addRecipe(is(EMBlocks.immortalCoal), is(EMItems.immortalDiamond));
        addRecipe(is(EMBlocks.naturalCoal), is(EMItems.naturalDiamond));
        addRecipe(is(EMBlocks.phantomCoal), is(EMItems.phantomDiamond));
    }

    private static ItemStack is(Item item) {
        return new ItemStack(item);
    }

    private static ItemStack is(Block block) {
        return new ItemStack(block);
    }
}

