package ru.mousecray.endmagic.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EMRecipes {
    public static void initRecipes() {
        GameRegistry.addSmelting(new ItemStack(EMBlocks.dragonLog), new ItemStack(EMItems.dragonCoal), 0.2F);
        GameRegistry.addSmelting(new ItemStack(EMBlocks.naturalLog), new ItemStack(EMItems.naturalCoal), 0.2F);
        GameRegistry.addSmelting(new ItemStack(EMBlocks.immortalLog), new ItemStack(EMItems.immortalCoal), 0.2F);
        GameRegistry.addSmelting(new ItemStack(EMBlocks.phantomLog), new ItemStack(EMItems.phantomCoal), 0.2F);
    }
}