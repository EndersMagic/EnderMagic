package ru.mousecray.endmagic.gameobj.items.tools;

import net.minecraft.item.ItemSpade;

public class EMShovel extends ItemSpade implements ItemTexturedTool {

    private final String name;
    private final int color;

    public EMShovel(ToolMaterial material, String name, int color) {
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
        return "shovel";
    }

    @Override
    public int color() {
        return color;
    }
}
