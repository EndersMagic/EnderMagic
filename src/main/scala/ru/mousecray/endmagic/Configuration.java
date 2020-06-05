package ru.mousecray.endmagic;

import net.minecraftforge.common.config.Config;

@Config(modid = EM.ID)
public class Configuration {
    @Config.Comment("Vertical limit for portals")
    @Config.RangeInt(min = 1)
    public static int portalSizeLimit = 10;

    @Config.Comment({"Controls size of end biomes. Larger number = larger biomes", "Default: 4"})
    @Config.RequiresWorldRestart
    public static int endBiomeSize = 4;

    @Config.Comment({"Reduce number of end biomes by percent (range 0-99). e.g. 40 would generate 40% fewer end biomes", "Default: 0"})
    @Config.RequiresWorldRestart
    public static int biomeReducer = 0;
}
