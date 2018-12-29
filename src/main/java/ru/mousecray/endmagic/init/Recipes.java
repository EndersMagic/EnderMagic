package ru.mousecray.endmagic.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.blocks.ListBlock;
import ru.mousecray.endmagic.items.ListItem;

public class Recipes {
	public static void init() {
		GameRegistry.addSmelting(Items.ENDER_PEARL, new ItemStack(ListItem.ENDERITE_BRICK), 0.3F);
		GameRegistry.addSmelting(ListBlock.ROUGH_ENDERITE, new ItemStack(ListBlock.TECH_ENDERITE, 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ListBlock.ENDER_LOGS, 1, 0), new ItemStack(ListItem.ENDER_COALS, 1, 0), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ListBlock.ENDER_LOGS, 1, 1), new ItemStack(ListItem.ENDER_COALS, 1, 1), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ListBlock.ENDER_LOGS, 1, 2), new ItemStack(ListItem.ENDER_COALS, 1, 2), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ListBlock.ENDER_LOGS, 1, 3), new ItemStack(ListItem.ENDER_COALS, 1, 3), 0.7F);
	}
}
