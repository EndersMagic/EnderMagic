package ru.mousecray.endmagic.util;

import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.items.ItemTextured;

import java.io.IOException;
import java.io.InputStream;

public class ModelUtils
{
    public static boolean isModelExists(ResourceLocation modelResourceLocation) {
        InputStream inputStream = ItemTextured.class.getResourceAsStream("/assets/" + modelResourceLocation.getResourceDomain() + "/models/item/" + modelResourceLocation.getResourcePath() + ".json");
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
            return true;
        } else
            return false;
    }
}
