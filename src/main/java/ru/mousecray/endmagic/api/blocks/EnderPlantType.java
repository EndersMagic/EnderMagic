package ru.mousecray.endmagic.api.blocks;

import net.minecraftforge.common.EnumPlantType;

public class EnderPlantType {
    public static final EnumPlantType end = EnumPlantType.getPlantType("end");
    public static final EnumPlantType em_hang = EnumPlantType.getPlantType("em_hang");
    public static final EnumPlantType end_crop = EnumPlantType.getPlantType("end_crop");
    public static final EnumPlantType em_wall = EnumPlantType.getPlantType("em_wall");
    
    public static boolean isEMPlantType(EnumPlantType type) {
    	return type == end || type == em_hang || type == end_crop || type == em_wall;
    }
}