package ru.mousecray.endmagic;

import net.minecraftforge.common.config.Config;

@Config(modid = EM.ID)
public class Configuration {
    @Config.Comment("Vertical limit for portals")
    @Config.RangeInt(min = 1)
    public static int portalSizeLimit = 10;
}
