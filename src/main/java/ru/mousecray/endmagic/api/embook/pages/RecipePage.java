package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.Rectangle;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ImageView;
import ru.mousecray.endmagic.client.gui.elements.ItemStackView;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RecipePage extends ImagePage {
    public final ItemStack result;
    public final ImmutableList<ImmutableList<Ingredient>> cratingGrid;

    public RecipePage(ItemStack result, ImmutableList<ImmutableList<Ingredient>> cratingGrid) {
        super(new ResourceLocation(EM.ID, "textures/gui/crafting_grid.png"), result.getDisplayName());
        this.result = result;
        this.cratingGrid = cratingGrid;
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        ImmutableList.Builder<IStructuralGuiElement> builder = ImmutableList.builder();
        builder.add(new ItemStackView(result, BookApi.pageWidth - 20, BookApi.pageHeight / 2 - 8));
        builder.addAll(super.elements());
        for (int x = 0; x < cratingGrid.size(); x++) {
            ImmutableList<Ingredient> col = cratingGrid.get(x);
            for (int y = 0; y < col.size(); y++)
                builder.add(new ItemStackView(ImmutableList.copyOf(col.get(y).getMatchingStacks()), x * 16, BookApi.pageHeight / 2 - 8 - 16 + y * 16));
        }
        return builder.build();
    }
}
