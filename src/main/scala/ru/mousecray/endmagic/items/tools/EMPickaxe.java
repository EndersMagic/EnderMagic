package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemPickaxe;
import ru.mousecray.endmagic.util.NameProvider;

public class EMPickaxe extends ItemPickaxe implements NameProvider {

    private String name;

    public EMPickaxe(ToolMaterial material, String name) {
        super(material);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
