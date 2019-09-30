package ru.mousecray.endmagic.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class RecipeHelper {
    public static ImmutableList<ImmutableList<ItemStack>> findRecipeGrid(ItemStack r) {
        Optional<IRecipe> maybeRecipe = GameRegistry.findRegistry(IRecipe.class)
                .getValuesCollection()
                .stream()
                .filter(i -> ItemStack.areItemsEqual(i.getRecipeOutput(), r))
                .findFirst();

        return maybeRecipe.map(recipe -> {

            int w, h;
            if (recipe instanceof IShapedRecipe) {
                w = ((IShapedRecipe) recipe).getRecipeWidth();
                h = ((IShapedRecipe) recipe).getRecipeHeight();
            } else {
                w = h = 3;
            }

            int[] array = {1, 2, 3, 4, 5};
            int[] copy = Arrays.copyOf(array, 5);

            ItemStack[][] cratingGrid1 = {
                    {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
                    {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
                    {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
                    {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY}
            };

            NonNullList<Ingredient> ingredients = recipe.getIngredients();

            System.out.println("test");
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    int index = x + y * w;
                    cratingGrid1[x][y] = first(ingredients.get(index).getMatchingStacks());
                }
            }
            return Stream.of(cratingGrid1).map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList());
        }).orElseGet(() -> {
            ImmutableList<ItemStack> e = ImmutableList.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
            return ImmutableList.of(e, e, e);
        });
    }

    private static ItemStack first(ItemStack[] matchingStacks) {
        if (matchingStacks.length > 0)
            return matchingStacks[0];
        else
            return ItemStack.EMPTY;
    }
}
