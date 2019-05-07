package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderDiamond extends ItemNamed {
    public EnderDiamond(String name, int color) {
        super(name, ImmutableMap.of(EM.ID + ":items/colorless_diamond", color));
    }
}