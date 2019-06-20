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

    public SmeltingRecipeComponent(ItemStack result, ItemStack input) {
        this.result = result;
        this.input = input;
    }

    public SmeltingRecipeComponent(ItemStack result) {
        this.result = result;
        Optional<Map.Entry<ItemStack, ItemStack>> maybeRecipe = FurnaceRecipes.instance().getSmeltingList().entrySet().stream().filter(i -> ItemStack.areItemStacksEqual(i.getValue(), result)).findAny();
        input = maybeRecipe.map(Map.Entry::getKey).orElse(new ItemStack(Item.getItemFromBlock(AIR)));
    }

    @Override
    public List<IPage> pages() {
        return ImmutableList.of(new SmeltingRecipePage(result, input));
    }
}
