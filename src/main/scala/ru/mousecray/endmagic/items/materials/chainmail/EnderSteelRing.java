package ru.mousecray.endmagic.items.materials.chainmail;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderSteelRing extends ItemNamed {
    public EnderSteelRing(String name, int color) {
        super(name+"_steel_ring", ImmutableMap.of(EM.ID + ":items/colorless_ring", color | 0xff000000));
    }
}
