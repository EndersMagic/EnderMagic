package ru.mousecray.endmagic.integration.explode;

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
    public static List<ExplodeRecipe> getRecipes() { // Получатель всех рецептов.
        return recipes;
    }

    private final ItemStack input, output; // Компоненты крафта.

    public ExplodeRecipe(ItemStack input, ItemStack output) { // Конструктор рецепта.
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() { // Получатель входного предмета рецепта.
        return input;
    }

    public ItemStack getOutput() { // Получатель выходного предмета рецепта.
        return output.copy();
    }

    public static ExplodeRecipe addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output) { // Метод добавления рецепта.
        ExplodeRecipe recipe = new ExplodeRecipe(input, output); // Создаем рецепт.
        //if (recipes.contains(recipe)) // Если он есть уже в рецептах - игнорим.
       //     return null;
       // else
            recipes.add(recipe); // Если же нет - добавляем.
        return recipe;
    }

    public static ExplodeRecipe getRecipe(ItemStack is) { // Получатель рецепта через входной предмет.
        if (is == null || is.isEmpty())
            return EMPTY;
        for (ExplodeRecipe recipe : recipes) // Проходим по списку всех рецептов.
            if (recipe.matchesInput(is)) // Сравниваем входные элементы.
                return recipe; // Возвращаем рецепт, если входные элементы одинаковые.
        return EMPTY;
    }

    public static ExplodeRecipe getRecipe(Block block) { // Получатель рецепта через входной блок.
        if (block == null)
            return EMPTY;
        for (ExplodeRecipe recipe : recipes) // Проходим по списку всех рецептов.
            if (recipe.getInput().getItem() == new ItemStack(block).getItem()) // Сравниваем входные элементы.
                return recipe; // Возвращаем рецепт, если входные элементы одинаковые.
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

    private static ItemStack is(Item item) { // Побочный метод.
        return new ItemStack(item);
    }

    private static ItemStack is(Block block) { // Побочный метод.
        return new ItemStack(block);
    }
}

