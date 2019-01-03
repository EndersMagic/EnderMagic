package ru.mousecray.endmagic.init;

import net.minecraft.init.Blocks;
import ru.mousecray.endmagic.items.EMSeeds;
import ru.mousecray.endmagic.items.ItemNamed;
import ru.mousecray.endmagic.items.ItemPortalBinder;

public class EMItems {
    //Not finished
    public static final EMSeeds enderSeeds = new EMSeeds(EMBlocks.enderCrops, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds");
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();
    public static final ItemNamed dragonCoal = new ItemNamed("");
    public static final ItemNamed naturalCoal = new ItemNamed("");
    public static final ItemNamed phantomCoal = new ItemNamed("");
    public static final ItemNamed immortaolCoal = new ItemNamed("");
    public static final ItemNamed dragonSteel = new ItemNamed("");
    public static final ItemNamed naturalSteel = new ItemNamed("");
    public static final ItemNamed phantomSteel = new ItemNamed("");
    public static final ItemNamed immortaolSteel = new ItemNamed("");
    public static final ItemNamed dragonDiamond = new ItemNamed("");
    public static final ItemNamed naturalDiamond = new ItemNamed("");
    public static final ItemNamed phantomDiamond = new ItemNamed("");
    public static final ItemNamed immortaolDiamond = new ItemNamed("");
}
