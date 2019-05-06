package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.util.NameProvider;

import java.util.Map;

public class ItemNamed extends ItemTextured implements NameProvider {

    public ItemNamed(String name) {
        this.name = name;
        textures = ImmutableMap.of(EM.ID + ":items/" + name, 0xffffff);
    }

    public ItemNamed(String name, Map<String, Integer> textures) {
        this.name = name;
        this.textures = textures;
    }

    private String name;
    private final Map<String, Integer> textures;

    @Override
    public String name() {
        return name;
    }

    public Map<String, Integer> textures() {
        return textures;
    }
}
