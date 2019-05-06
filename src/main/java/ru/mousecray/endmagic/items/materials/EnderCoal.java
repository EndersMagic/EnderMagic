package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderCoal extends ItemNamed {
    public EnderCoal(String name, int color) {
        super(name, ImmutableMap.of("colorless_coal", color));
    }
}
