package ru.mousecray.endmagic.items.materials;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemNamed;

public class BaseMaterial extends ItemNamed {
    public BaseMaterial(String name, String additionName, int color) {
        super(name + additionName, ImmutableMap.of(EM.ID + ":items/colorless" + additionName, color | 0xff000000));
    }
}
