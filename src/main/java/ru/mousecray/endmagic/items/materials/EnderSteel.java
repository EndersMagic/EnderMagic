package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderSteel extends ItemNamed {
    public EnderSteel(String name, int color) {
        super(name, ImmutableMap.of(EM.ID + ":items/colorless_steel", color));
    }
}