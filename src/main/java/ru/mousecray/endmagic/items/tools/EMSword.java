package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemSword;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.registry.NameProvider;

public class EMSword extends ItemSword implements NameProvider, ItemTexturedTool {

    private final String name;
    private final int color;

    public EMSword(ToolMaterial material, String name, int color) {
        super(material);
        this.name = name;
        this.color = color;
    }

    @Override
    public String name() {
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
