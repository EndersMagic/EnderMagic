package ru.mousecray.endmagic.api.embook.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.api.embook.IChapterComponent;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.pages.RecipePage;
import ru.mousecray.endmagic.api.embook.pages.SmeltingRecipePage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.init.Blocks.AIR;

public class SmeltingRecipeComponent implements IChapterComponent {
    public final ItemStack result;
    public final ItemStack input;
    private String label;

    public SmeltingRecipeComponent(ItemStack result, ItemStack input, String label) {
        this.result = result;
        this.input = input;
        this.label = label;
    }

    public SmeltingRecipeComponent(ItemStack result) {
        this(result, result.getDisplayName());
    }

    public SmeltingRecipeComponent(ItemStack result, String label) {
        this(result,
                FurnaceRecipes.instance().getSmeltingList().entrySet()
                        .stream()
                        .filter(i -> ItemStack.areItemStacksEqual(i.getValue(), result))
                        .findAny()
                        .map(Map.Entry::getValue)
                        .orElse(new ItemStack(Item.getItemFromBlock(AIR))),
                label);
    }

    @Override
    public List<IPage> pages() {
        return ImmutableList.of(new SmeltingRecipePage(result, input, label));
    }
}
