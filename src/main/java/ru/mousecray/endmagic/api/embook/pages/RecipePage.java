package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.ILinkLocation;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ItemStackView;
import ru.mousecray.endmagic.util.ItemStackMapKey;

import java.util.List;

public class RecipePage extends ImagePage implements ILinkLocation<ItemStackMapKey> {
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
        for (int y = 0; y < cratingGrid.size(); y++) {
            ImmutableList<Ingredient> col = cratingGrid.get(y);
            for (int x = 0; x < col.size(); x++)
                builder.add(new ItemStackView(ImmutableList.copyOf(col.get(x).getMatchingStacks()), x * 16, BookApi.pageHeight / 2 - 8 - 16 + y * 16));
        }
        return builder.build();
    }

    @Override
    public ItemStackMapKey linkObject() {
        return new ItemStackMapKey(result);
    }
}
