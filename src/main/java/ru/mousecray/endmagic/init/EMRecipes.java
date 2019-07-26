package ru.mousecray.endmagic.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EMRecipes {
	public static void initRecipes() {
		GameRegistry.addSmelting(new ItemStack(EMBlocks.enderLog, 1, 0), new ItemStack(EMItems.dragonCoal), 0.2F);
		GameRegistry.addSmelting(new ItemStack(EMBlocks.enderLog, 1, 1), new ItemStack(EMItems.naturalCoal), 0.2F);
		GameRegistry.addSmelting(new ItemStack(EMBlocks.enderLog, 1, 2), new ItemStack(EMItems.immortalCoal), 0.2F);
		GameRegistry.addSmelting(new ItemStack(EMBlocks.enderLog, 1, 3), new ItemStack(EMItems.phantomCoal), 0.2F);
	}
}