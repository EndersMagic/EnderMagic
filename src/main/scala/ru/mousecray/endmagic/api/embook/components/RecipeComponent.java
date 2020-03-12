package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.RecipePage;
import ru.mousecray.endmagic.util.RecipeHelper;

import java.util.List;

public class RecipeComponent implements IChapterComponent {
    public final ImmutableList<ItemStack> result;
    public final ImmutableList<ImmutableList<ImmutableList<ItemStack>>> cratingGrid;
    public final String label;

    public RecipeComponent(ImmutableList<ItemStack> result, ImmutableList<ImmutableList<ImmutableList<ItemStack>>> cratingGrid, String label) {
        this.result = result;
        this.cratingGrid = cratingGrid;
        this.label = label;
    }

    public RecipeComponent(ItemStack itemStack) {
        this(ImmutableList.of(itemStack), ImmutableList.of(RecipeHelper.findRecipeGrid(itemStack)), itemStack.getDisplayName());
    }

    /**
     * Constructor where find and prepare recipe grid for ItemStack
     */
        /*
        Optional<IRecipe> recipe = GameRegistry.findRegistry(IRecipe.class)
                .getValuesCollection()
                .stream()
                .filter(i -> ItemStack.areItemsEqual(i.getRecipeOutput(), listOfResult))
                .findFirst();
        Ingredient[][] cratingGrid1 = new Ingredient[3][3];

        Ingredient emptyStack = Ingredient.fromStacks(ItemStack.EMPTY);

        NonNullList<Ingredient> ingredients =
                recipe
                        .map(IRecipe::getIngredients)
                        .orElseGet(() -> NonNullList.from(emptyStack));

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int index = y + x * 3;
                if (ingredients.size() > index)
                    cratingGrid1[x][y] = ingredients.get(index);
                else
                    cratingGrid1[x][y] = emptyStack;
            }
        }

        listOfCratingGrid = Stream.of(cratingGrid1).map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList());*/
    @Override
    public List<IPage> pages() {
        return ImmutableList.of(new RecipePage(result, cratingGrid, label));
    }
}
