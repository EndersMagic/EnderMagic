package ru.mousecray.endmagic.items;

import net.minecraft.item.Item;
import ru.mousecray.endmagic.util.NameProvider;

public class ItemNamed extends Item implements NameProvider {
    public ItemNamed(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String name() {
        return name;
    }
}
