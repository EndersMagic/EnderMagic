package ru.mousecray.endmagic.api.metadata;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyEnum;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.Predicate;

public class PropertyFeature<T extends Enum<T> & IFeaturesList> extends PropertyEnum<T> {

    private final boolean hasItemBlock;

    protected PropertyFeature(String name, Class<T> valueClass, Collection<T> allowedValues, boolean hasItemBlock) {
        super(name, valueClass, allowedValues);
        this.hasItemBlock = hasItemBlock;
    }

    public boolean hasItemBlock() {
        return hasItemBlock;
    }

    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz) {
        return createProperty(name, clazz, false, t -> true);
    }

    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, boolean hasItemBlock) {
        return createProperty(name, clazz, hasItemBlock, t -> true);
    }


    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, boolean hasItemBlock, @Nonnull Predicate<T> filter) {
        return createProperty(name, clazz, hasItemBlock, Collections2.filter(Lists.newArrayList(clazz.getEnumConstants()), filter::test));
    }

    @Nonnull
    @SafeVarargs
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, T... values) {
        return createProperty(name, clazz, false, Lists.newArrayList(values));
    }

    @Nonnull
    @SafeVarargs
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, boolean hasItemBlock, T... values) {
        return createProperty(name, clazz, hasItemBlock, Lists.newArrayList(values));
    }


    @Nonnull
    public static <T extends Enum<T> & IFeaturesList> PropertyFeature<T> createProperty(String name, Class<T> clazz, boolean hasItemBlock, Collection<T> values) {
        return new PropertyFeature<>(name, clazz, values, hasItemBlock);
    }
}