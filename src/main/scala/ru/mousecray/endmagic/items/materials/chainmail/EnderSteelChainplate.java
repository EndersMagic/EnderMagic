package ru.mousecray.endmagic.items.materials.chainmail;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderSteelChainplate extends ItemNamed {
    public EnderSteelChainplate(String name, int color) {
        super(name + "_steel_chainplate", ImmutableMap.of(EM.ID + ":items/colorless_chainplate", color | 0xff000000));
    }
}
