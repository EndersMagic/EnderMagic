package ru.mousecray.endmagic.items.tools;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemTextured;

import java.util.Map;

public interface ItemTexturedTool extends ItemTextured {
    @Override
	default Map<String, Integer> textures() {
        return ImmutableMap.of(
                EM.ID + ":items/tools/colorless_" + toolType(), color());
    }

    default String stick() {
        return "minecraft:items/stick";
    }

    String toolType();

    int color();
}
