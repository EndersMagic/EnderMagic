package ru.mousecray.endmagic.gameobj.items.tools;

import net.minecraft.item.ItemHoe;

public class EMHoe extends ItemHoe implements ItemTexturedTool {

    private final String name;
    private final int color;

    public EMHoe(ToolMaterial material, String name, int color) {
        super(material);
        this.name = name;
        this.color = color;
    }

    @Override
    public String getCustomName() {
        return name;
    }

    @Override
    public String toolType() {
        return "hoe";
    }

    @Override
    public int color() {
        return color;
    }
}
