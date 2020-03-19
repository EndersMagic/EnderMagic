package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemAxe;

public class EMAxe extends ItemAxe implements ItemTexturedTool {

    private final String name;
    private final int color;

    public EMAxe(ToolMaterial material, String name, int color) {
        super(material, 8.5F, -3.1F);
        this.name = name;
        this.color = color;
    }

    @Override
    public String getCustomName() {
        return name;
    }

    @Override
    public String toolType() {
        return "axe";
    }

    @Override
    public int color() {
        return color;
    }
}
