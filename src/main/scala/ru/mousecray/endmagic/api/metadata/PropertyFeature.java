package ru.mousecray.endmagic.api.metadata;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyEnum;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.Predicate;

public class PropertyFeature<T extends Enum<T> & IFeaturesList> extends PropertyEnum<T> {

    protected PropertyFeature(String name, Class<T> valueClass, Collection<T> allowedValues) {
        super(name, valueClass, allowedValues);
    }

    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz) {
        return createProperty(name, clazz, t -> true);
    }


    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, @Nonnull Predicate<T> filter) {
        return createProperty(name, clazz, Collections2.filter(Lists.newArrayList(clazz.getEnumConstants()), filter::test));
    }

    @Nonnull
    @SafeVarargs
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, T... values) {
        return createProperty(name, clazz, Lists.newArrayList(values));
    }


    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, Collection<T> values) {
        return new PropertyFeature<>(name, clazz, values);
    }
}