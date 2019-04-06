package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ImageView;
import ru.mousecray.endmagic.client.gui.elements.ItemStackView;

import java.util.List;
import java.util.stream.Stream;

public class RecipePage implements IPage {
    public final ItemStack result;
    public final ImmutableList<ImmutableList<Ingredient>> cratingGrid;

    public RecipePage(ItemStack result, ImmutableList<ImmutableList<Ingredient>> cratingGrid) {
        this.result = result;
        this.cratingGrid = cratingGrid;
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
