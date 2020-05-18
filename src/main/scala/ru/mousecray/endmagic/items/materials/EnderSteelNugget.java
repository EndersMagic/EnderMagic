package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderSteelNugget extends ItemNamed {
    public EnderSteelNugget(String name, int color) {
        super(name, ImmutableMap.of(EM.ID + ":items/colorless_steel_nugget", color | 0xff000000));
    }
}
