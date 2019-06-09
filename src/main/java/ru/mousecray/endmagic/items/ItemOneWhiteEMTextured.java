package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;

import java.util.Map;

public interface ItemOneWhiteEMTextured extends ItemTextured {
    String texture();

    @Override
    default Map<String, Integer> textures() {
        return ImmutableMap.of(EM.ID + ":items/" + texture(), 0xffffffff);
    }
}
