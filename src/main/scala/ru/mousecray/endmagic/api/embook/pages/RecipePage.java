package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.ILinkLocation;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ItemStackView;
import ru.mousecray.endmagic.util.ItemStackMapKey;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class RecipePage extends ImagePage implements ILinkLocation<ImmutableList<ItemStackMapKey>> {
    public final ImmutableList<ItemStack> listOfResult;
    public final ImmutableList<ImmutableList<ImmutableList<ItemStack>>> listOfCratingGrid;

    public RecipePage(ImmutableList<ItemStack> listOfResult, ImmutableList<ImmutableList<ImmutableList<ItemStack>>> listOfCratingGrid, String label) {
        super(new ResourceLocation(EM.ID, "textures/gui/crafting_grid.png"), label);
        this.listOfResult = listOfResult;
        this.listOfCratingGrid = listOfCratingGrid;
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        ImmutableList.Builder<IStructuralGuiElement> builder = ImmutableList.builder();

        builder.addAll(super.elements());

        builder.add(new ItemStackView(listOfResult, BookApi.pageWidth - 20, BookApi.pageHeight / 2 - 8));


        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                ImmutableList.Builder<ItemStack> slotOfGrid = ImmutableList.builder();
                for (ImmutableList<ImmutableList<ItemStack>> grid : listOfCratingGrid)
                    slotOfGrid.add(grid.get(x).get(y));
                builder.add(new ItemStackView(slotOfGrid.build(), x * 16, BookApi.pageHeight / 2 - 8 - 16 + y * 16));

            }


        return builder.build();
    }

    @Override
    public ImmutableList<ItemStackMapKey> linkObject() {
        return listOfResult.stream().map(ItemStackMapKey::new).collect(toImmutableList());
    }
}
