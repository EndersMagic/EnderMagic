package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.client.gui.IStructuralGuiElement;
import ru.mousecray.endmagic.client.gui.elements.ItemStackView;

import java.util.List;

public class SmeltingRecipePage extends ImagePage {
    public final ItemStack result;
    private final ItemStack input;

    public SmeltingRecipePage(ItemStack result, ItemStack input,String label) {
        super(new ResourceLocation(EM.ID, "textures/gui/smelting_grid.png"), label);
        this.result = result;
        this.input = input;
    }



    @Override
    public List<IStructuralGuiElement> elements() {
        ImmutableList.Builder<IStructuralGuiElement> builder = ImmutableList.builder();
        builder.addAll(super.elements());
        builder.add(new ItemStackView(result, BookApi.pageWidth - 31, BookApi.pageHeight / 2 - 8));
        builder.add(new ItemStackView(result, 9, BookApi.pageHeight / 2 - 27));
        return builder.build();
    }
}