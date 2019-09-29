package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.SmeltingRecipePage;

import java.util.List;
import java.util.Map;

import static net.minecraft.init.Blocks.AIR;

public class SmeltingRecipeComponent implements IChapterComponent {
    public final ImmutableList<ItemStack> result;
    public final ImmutableList<ItemStack> input;
    private String label;

    public SmeltingRecipeComponent(ImmutableList<ItemStack> result, ImmutableList<ItemStack> input, String label) {
        this.result = result;
        this.input = input;
        this.label = label;
    }

    public SmeltingRecipeComponent(ItemStack result, ItemStack input, String label) {
        this.result = ImmutableList.of(result);
        this.input = ImmutableList.of(input);
        this.label = label;
    }

    public SmeltingRecipeComponent(ItemStack result) {
        this(result, result.getDisplayName());
    }

    public SmeltingRecipeComponent(ItemStack result, String label) {
        this(ImmutableList.of(result), label);
    }

    public SmeltingRecipeComponent(ImmutableList<ItemStack> result, String label) {
        this(result,
                result.stream().map(r ->
                        FurnaceRecipes.instance().getSmeltingList().entrySet()
                                .stream()
                                .filter(i -> ItemStack.areItemStacksEqual(i.getValue(), r))
                                .findAny()
                                .map(Map.Entry::getKey)
                                .orElse(new ItemStack(Item.getItemFromBlock(AIR))))
                        .collect(ImmutableList.toImmutableList()),
                label);
    }

    @Override
    public List<IPage> pages() {
        return ImmutableList.of(new SmeltingRecipePage(result, input, label));
    }
}
