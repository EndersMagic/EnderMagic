package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemSword;
import ru.mousecray.endmagic.EM;

public class EMSword extends ItemSword implements ItemTexturedTool {

    private final String name;
    private final int color;

    public EMSword(ToolMaterial material, String name, int color) {
        super(material);
        this.name = name;
        this.color = color;
    }

    @Override
    public String getCustomName() {
        return name;
    }

    @Override
    public String stick() {
        return EM.ID + ":items/tools/sword_stick";
    }

    @Override
    public String toolType() {
        return "sword";
    }

    @Override
    public int color() {
        return color;
    }
}
