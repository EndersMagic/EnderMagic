package ru.mousecray.endmagic.items;

import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.proxy.ClientProxy;
import ru.mousecray.endmagic.util.NameProvider;

public class ItemNamed extends ItemTextured implements NameProvider {

    public ItemNamed(String name) {
        this.name = name;
        ClientProxy.registerTexture(new ResourceLocation(textureName()));
    }

    private String name;

    @Override
    public String name() {
        return name;
    }

    public String textureName() {
        return EM.ID + ":items/" + name;
    }
}