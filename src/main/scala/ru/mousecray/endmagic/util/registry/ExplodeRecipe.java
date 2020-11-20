package ru.mousecray.endmagic.util.registry;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.init.EMItems;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExplodeRecipe {
    private static final Map<Block, ItemStack> recipes = new HashMap<>();

    public static Map<Block, ItemStack>  getRecipes() {
        return recipes;
    }

    public static void addRecipe(@Nonnull Block input, @Nonnull ItemStack output) {
        recipes.put(input, output);
    }

    public static ItemStack getRecipe(Block block) {
        return recipes.getOrDefault(block, ItemStack.EMPTY);
    }

    public static void initRecipes() { // Метод регистрации рецептов.
        addRecipe(EMBlocks.dragonCoal,   is(EMItems.dragonDiamond));
        addRecipe(EMBlocks.immortalCoal, is(EMItems.immortalDiamond));
        addRecipe(EMBlocks.naturalCoal,  is(EMItems.naturalDiamond));
        addRecipe(EMBlocks.phantomCoal,  is(EMItems.phantomDiamond));
    }

    private static ItemStack is(Item item) {
        return new ItemStack(item);
    }
}

