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

public class SmeltingRecipePage extends ImagePage implements ILinkLocation<ItemStackMapKey> {
    public final ImmutableList<ItemStack> result;
    private final ImmutableList<ItemStack> input;

    public SmeltingRecipePage(ImmutableList<ItemStack> result, ImmutableList<ItemStack> input, String label) {
        super(new ResourceLocation(EM.ID, "textures/gui/smelting_grid.png"), label);
        this.result = result;
        this.input = input;
    }


    @Override
    public List<IStructuralGuiElement> elements() {
        ImmutableList.Builder<IStructuralGuiElement> builder = ImmutableList.builder();
        builder.addAll(super.elements());
        builder.add(new ItemStackView(result, BookApi.pageWidth - 31, BookApi.pageHeight / 2 - 8));
        builder.add(new ItemStackView(input, 9, BookApi.pageHeight / 2 - 27));
        return builder.build();
    }

    @Override
    public ItemStackMapKey linkObject() {
        return new ItemStackMapKey(result.iterator().next());
    }
}