package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.EM;

import java.util.Map;

public class ItemNamed extends Item implements ItemTextured {

    private final Map<String, Integer> textures;
    private String name;

    public ItemNamed(String name) {
        this.name = name;
        textures = ImmutableMap.of(EM.ID + ":items/" + name, 0xffffffff);
    }

    public ItemNamed(String name, Map<String, Integer> textures) {
        this.name = name;
        this.textures = textures;
    }

    @Override
    public String getCustomName() {
        return name;
    }

    @Override
    public Map<String, Integer> textures() {
        return textures;
    }
}
