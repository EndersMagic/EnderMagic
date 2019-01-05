package ru.mousecray.endmagic.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.items.BlueEnderPearl;
import ru.mousecray.endmagic.items.EMSeeds;
import ru.mousecray.endmagic.items.*

public class EMItems {
    public static final Item enderSeeds = new EMSeeds(EMBlocks.enderCrops, Blocks.END_STONE, "ender_seeds", "tooltip.ender_seeds");
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();

    public static final ItemTextured simpletexturemodel = ItemTextured.companion.simpletexturemodelItem;

    public static final ItemNamed dragonCoal = new ItemNamed("dragon_coal");
    public static final ItemNamed naturalCoal = new ItemNamed("natural_coal");
    public static final ItemNamed phantomCoal = new ItemNamed("phantom_coal");
    public static final ItemNamed immortalCoal = new ItemNamed("immortal_coal");

    public static final ItemNamed dragonSteel = new ItemNamed("dragon_steel");
    public static final ItemNamed naturalSteel = new ItemNamed("natural_steel");
    public static final ItemNamed phantomSteel = new ItemNamed("phantom_steel");
    public static final ItemNamed immortalSteel = new ItemNamed("immortal_steel");

    public static final ItemNamed dragonDiamond = new ItemNamed("dragon_diamond");
    public static final ItemNamed naturalDiamond = new ItemNamed("natural_diamond");
    public static final ItemNamed phantomDiamond = new ItemNamed("phantom_diamond");
    public static final ItemNamed immortalDiamond = new ItemNamed("immortal_diamond");
    
	  public static final Item purpleEnderPearl = new PurpleEnderPearl();
    public static final Item blueEnderPearl = new BlueEnderPearl();
    public static final Item enderArrow = new EnderArrow();
}
