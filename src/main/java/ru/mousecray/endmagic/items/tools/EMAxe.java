package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemAxe;
import ru.mousecray.endmagic.util.NameProvider;

public class EMAxe extends ItemAxe implements NameProvider, ItemTexturedTool {

    private final String name;
    private final int color;

    public EMAxe(ToolMaterial material, String name, int color) {
        super(material, 8.5F, -3.1F);
        this.name = name;
        this.color = color;
    }

    @Override
    public String name() {
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
