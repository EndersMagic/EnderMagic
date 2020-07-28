package ru.mousecray.endmagic.util;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ResourcesUtils {

    public static List<String> listResources(String folder, Predicate<String> filter) {
        String normalizedFolder = normalize(folder);
        List<String> r = new ArrayList<>();

        CraftingHelper.findFiles(Loader.instance().activeModContainer(), normalizedFolder.substring(1, normalizedFolder.length() - 1), null, (root, resource) -> {
            String filename = root.relativize(resource).toString();
            if (filter.test(filename)) {
                r.add(normalizedFolder + filename);
            }

            return true;
        }, true, true);

        return r;
    }

    private static String normalize(String folder) {
        if (!folder.startsWith("/"))
            folder = "/" + folder;
        if (!folder.endsWith("/"))
            folder = folder + "/";
        return folder.replace("\\\\", "\\");
    }

    public static String readResource(String resource) throws IOException {
        return IOUtils.toString(ResourcesUtils.class.getResourceAsStream(resource), StandardCharsets.UTF_8);
    }
}
