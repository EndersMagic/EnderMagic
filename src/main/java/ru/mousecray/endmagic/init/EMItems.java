package ru.mousecray.endmagic.init;

import net.minecraft.init.Blocks;
import ru.mousecray.endmagic.items.EMSeeds;
import ru.mousecray.endmagic.items.ItemPortalBinder;

public class EMItems {
    //Not finished
    public static final EMSeeds enderSeeds = new EMSeeds(EMBlocks.enderCrops, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds");
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();
}
