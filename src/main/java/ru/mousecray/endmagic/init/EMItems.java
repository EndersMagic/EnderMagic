package ru.mousecray.endmagic.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.items.BlueEnderPearl;
import ru.mousecray.endmagic.items.EMSeeds;
import ru.mousecray.endmagic.items.ItemPortalBinder;
import ru.mousecray.endmagic.items.PurpleEnderPearl;

public class EMItems {
    public static final Item enderSeeds = new EMSeeds(EMBlocks.enderCrops, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds");
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();
	public static final Item purpleEnderPearl = new PurpleEnderPearl();
    public static final Item blueEnderPearl = new BlueEnderPearl();
}
