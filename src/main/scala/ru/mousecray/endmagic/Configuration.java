package ru.mousecray.endmagic;

import net.minecraftforge.common.config.Config;

@Config(modid = EM.ID)
public class Configuration {
    @Config.Comment("Vertical limit for portals")
    @Config.RangeInt(min = 1)
    public static int portalSizeLimit = 10;


    @Config.Comment("Radius for searching portal frame for ender eye flying target")
    @Config.RangeInt(min = 10)
    public static int enderPortalFrameSearchRadius = 10;

    @Config.Comment("How long the portal will be opened. In ticks")
    @Config.RangeInt(min = 3)
    public static int portalOpenTime = 20 * 60;
}
