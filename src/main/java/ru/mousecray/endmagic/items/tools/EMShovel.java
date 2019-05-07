package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemSpade;
import ru.mousecray.endmagic.util.NameProvider;

public class EMShovel extends ItemSpade implements NameProvider, ItemTexturedTool {

    private final String name;
    private final int color;

    public EMShovel(ToolMaterial material, String name, int color) {
        super(material);
        this.name = name;
        this.color = color;
    }

    @Override
    public String name() {
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
