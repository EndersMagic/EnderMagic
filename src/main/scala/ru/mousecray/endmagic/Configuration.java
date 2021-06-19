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

    @Config.Comment("How many Em player can get on first spawn")
    public static int maxStartingEm = 1000;
    @Config.Comment("How small Em player can get on first spawn")
    public static int minStartingEm = 150;
    @Config.Comment("How many Em player can have")
    public static int maxEm = 10000;

    @Config.Comment("How many Em need to inscribe one rune piece")
    public static int emPerRunePart = 10;

    @Config.Comment("Runes will require reload by Em potion if this parameter is true")
    public static boolean exhaustibleRuneResource = false;

    @Config.Comment("How many times can be used rune with one part or how many minutes will be active. Full count of used or minutes is proportional to count of rune parts")
    public static int emResourcePerPart = 1000;

    @Config.Comment("How many Em resource will be restored by using Em potion")
    public static int emResourcePerPotion = 1000*15;
}
