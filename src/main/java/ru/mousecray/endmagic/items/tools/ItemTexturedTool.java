package ru.mousecray.endmagic.items.tools;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemTextured;

import java.util.Map;

public interface ItemTexturedTool extends ItemTextured {
    default Map<String, Integer> textures() {
        return ImmutableMap.of(
                EM.ID + ":items/tools/colorless_" + toolType(), color(),
                "minecraft:/items/stick", 0xffffffff);
    }

    String toolType();

    int color();
}
