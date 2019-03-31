package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ImageView;
import ru.mousecray.endmagic.client.gui.elements.ItemStackView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RecipePage implements IPage {
    public final ItemStack result;

    public RecipePage(ItemStack result, ImmutableList<ImmutableList<Ingredient>> cratingGrid) {
        this.result = result;
        this.cratingGrid = cratingGrid;
    }

    public final ImmutableList<ImmutableList<Ingredient>> cratingGrid;

    /**
     * Constructor where find and prepare recipe grid for ItemStack
     *
     * @param result
     */
    public RecipePage(ItemStack result) {
        this.result = result;
        Optional<IRecipe> recipe = GameRegistry.findRegistry(IRecipe.class)
                .getValuesCollection()
                .stream()
                .filter(i -> ItemStack.areItemsEqual(i.getRecipeOutput(), result))
                .findFirst();
        Ingredient[][] cratingGrid1 = new Ingredient[3][3];

        Ingredient emptyStack = Ingredient.fromStacks(ItemStack.EMPTY);

        NonNullList<Ingredient> ingredients =
                recipe
                        .map(IRecipe::getIngredients)
                        .orElseGet(() -> NonNullList.from(emptyStack));

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int index = x + y * 3;
                if (ingredients.size() > index)
                    cratingGrid1[x][y] = ingredients.get(index);
                else
                    cratingGrid1[x][y] = emptyStack;
            }
        }

        cratingGrid = Stream.of(cratingGrid1).map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return Stream.concat(
                Stream.of(
                        new ImageView(new ResourceLocation(EM.ID, "crafting_grid"), new Rectangle(0, 0, BookApi.pageWidth, BookApi.pageHeight)),
                        new ItemStackView(result, 0, 0)//todo: correct pos
                ),
                cratingGrid
                        .stream()
                        .flatMap(col -> col.stream()
                                .map(i -> new ItemStackView(ImmutableList.copyOf(i.matchingStacks), 0, 0 /*todo: correct pos*/))))
                .collect(ImmutableList.toImmutableList());
    }
}
