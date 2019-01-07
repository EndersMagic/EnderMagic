package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemAxe;
import ru.mousecray.endmagic.util.NameProvider;

public class EMAxe extends ItemAxe implements NameProvider {

    private String name;

    public EMAxe(ToolMaterial material, String name) {
        super(material, 8.5F, -3.1F);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
