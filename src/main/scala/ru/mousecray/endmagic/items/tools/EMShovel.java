package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemSpade;
import ru.mousecray.endmagic.util.NameProvider;

public class EMShovel extends ItemSpade implements NameProvider {

    private String name;

    public EMShovel(ToolMaterial material, String name) {
        super(material);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
