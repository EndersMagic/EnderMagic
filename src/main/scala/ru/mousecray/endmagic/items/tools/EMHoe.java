package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemHoe;
import ru.mousecray.endmagic.util.NameProvider;

public class EMHoe extends ItemHoe implements NameProvider {

    private String name;

    public EMHoe(ToolMaterial material, String name) {
        super(material);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
