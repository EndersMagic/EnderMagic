package ru.mousecray.endmagic.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.blocks.BlockBlastFurnace;
import ru.mousecray.endmagic.init.EMBlocks;
import ru.mousecray.endmagic.integration.blast.BlastCategory;
import ru.mousecray.endmagic.integration.blast.BlastCraftingWrapper;
import ru.mousecray.endmagic.integration.explode.ExplodeCategory;
import ru.mousecray.endmagic.integration.explode.ExplodeCraftingWrapper;
import ru.mousecray.endmagic.integration.explode.ExplodeRecipe;

@JEIPlugin
public class EMJEIPlugin implements IModPlugin
{
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) { // Регистрация категории, которая будет показываться в MC.
        registry.addRecipeCategories(new ExplodeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new BlastCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(ExplodeRecipe.getRecipes(), ExplodeCategory.UID); // Регистрация рецептов для Вашего UID.
        registry.handleRecipes(ExplodeRecipe.class, ExplodeCraftingWrapper::new, ExplodeCategory.UID); // Регистрация рецептов из листа рецептов.
        registry.addRecipeCatalyst(new ItemStack(Blocks.TNT), ExplodeCategory.UID); // Регистрация показывающихся блоков для Вашего UID.

        registry.addRecipes(EMBlocks.blockBlastFurnace.recipes(), BlastCategory.UID); // Регистрация рецептов для Вашего UID.
        registry.handleRecipes(BlockBlastFurnace.BlastRecipe.class, BlastCraftingWrapper::new, BlastCategory.UID); // Регистрация рецептов из листа рецептов.
        registry.addRecipeCatalyst(new ItemStack(EMBlocks.blockBlastFurnace), BlastCategory.UID); // Регистрация показывающихся блоков для Вашего UID.
    }
}
