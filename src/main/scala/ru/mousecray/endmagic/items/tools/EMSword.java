package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemSword;
import ru.mousecray.endmagic.util.NameProvider;

public class EMSword extends ItemSword implements NameProvider {

    private String name;

    public EMSword(ToolMaterial material, String name) {
        super(material);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
