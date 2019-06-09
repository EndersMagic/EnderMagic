package ru.mousecray.endmagic.util;

import java.util.stream.IntStream;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.EM;

public class NameAndTabUtils {
    public static String getName(Object c) {
        if (c instanceof NameProvider)
            return ((NameProvider) c).name();
        else
            return getName(c.getClass());
    }

    public static String getName(Class c) {
        String r = c.getSimpleName()
        		.chars()
        		.flatMap(i -> {
        			Character a = (char) i;
                    if (Character.isUpperCase(i)) {
                        return IntStream.of('_', Character.toLowerCase(i));
                    } else
                        return IntStream.of(i);
                })
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        if (r.startsWith("_"))
            return r.substring(1);
        else
            return r;
    }
    
    //getCreativeTab
    @Nullable
    public static CreativeTabs getCTab(Object c) {
        if (c instanceof CreativeTabProvider)
            return ((CreativeTabProvider) c).creativeTab();
        else
            return EM.EM_CREATIVE;
    }
}