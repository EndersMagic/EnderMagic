package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class EnderCoal extends ItemNamed {
    public EnderCoal(String name, int color) {
        super(name, ImmutableMap.of(EM.ID + ":items/colorless_coal", color | 0xff000000));
    }
}
