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

    @Config.Comment("How many hp player can loss when one Em not enough")
    @Config.RangeDouble()
    public static double healthPointPerEmPoint = 0.1;
}
