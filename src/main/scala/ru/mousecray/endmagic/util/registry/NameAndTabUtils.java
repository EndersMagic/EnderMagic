package ru.mousecray.endmagic.util.registry;

import net.minecraft.creativetab.CreativeTabs;
import ru.mousecray.endmagic.EM;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.stream.IntStream;

public class NameAndTabUtils {
    public static String getNameForRegistry(Object c) {
        if (c instanceof IExtendedProperties) {
            IExtendedProperties iExtendedProperties = (IExtendedProperties) c;
            String res = iExtendedProperties.getCustomName();
            return res != null ? res : getName(c);
        }
        else return getName(c);
    }

    public static String getName(Object c) {
        return getName(c.getClass());
    }

    public static String getName(Class c) {
        return toId(c.getSimpleName());
    }

    public static String getName(Class c, Function<String, String> formatter) {
        return formatter.apply(getName(c));
    }

    public static String toId(String r) {
        r = r.chars()
                .flatMap(i -> {
                    //TODO: Is used?!
                    Character a = (char) i;
                    if (Character.isUpperCase(i)) return IntStream.of('_', Character.toLowerCase(i));
                    else
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
        if (c instanceof IExtendedProperties) return ((IExtendedProperties) c).getCustomCreativeTab();
        else return EM.EM_CREATIVE;
    }
}