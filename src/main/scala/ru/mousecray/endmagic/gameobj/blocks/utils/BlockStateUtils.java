package ru.mousecray.endmagic.gameobj.blocks.utils;

import com.google.common.base.Defaults;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class BlockStateUtils {
    public static Pair<List<IBlockState>, Map<ImmutableMap<IProperty<?>, Comparable<?>>, Integer>> buildStateIsomorphism(List<IProperty<?>> properties) {
        return null;
    }

    public static Map<FeatureTypes, PropertyFeature> prepareFeatures(PropertyFeature itself, List<PropertyFeature> features) {
        List<FeatureListWrapper> featureListWrappers = analiseFeatures(features);
        try {
            return featureListWrappers
                    .stream()
                    .flatMap(w -> w.availableFeatures.stream().map(ft -> Pair.of(ft, w.feature)))
                    .collect(toMap(Pair::getLeft, Pair::getRight, (u, v) -> {
                                throw new IllegalStateException(String.format("Duplicate key %s", u));
                            },
                            () -> new EnumMap<FeatureTypes, PropertyFeature>(FeatureTypes.class) {
                                @Override
                                public PropertyFeature get(Object key) {
                                    return getOrDefault(key, itself);
                                }
                            }));
        } catch (IllegalStateException duplicateKey) {
            throw new IllegalArgumentException("features containssw intersected IFeaturesList " + features);
        }
    }

    public static List<FeatureListWrapper> analiseFeatures(List<PropertyFeature> features) {
        return features.stream().map(f -> new FeatureListWrapper(f, analiseFeature(f))).collect(Collectors.toList());
    }

    private static List<FeatureTypes> analiseFeature(PropertyFeature<?> feature) {
        return Arrays.stream(FeatureTypes.values())
                .filter(isAvailableForOneOf(feature)).collect(Collectors.toList());
    }

    private static Predicate<FeatureTypes> isAvailableForOneOf(PropertyFeature<?> feature) {
        return featureType -> feature.getAllowedValues().stream().anyMatch(i -> isAvailableFor(i, featureType));
    }

    private static boolean isAvailableFor(IFeaturesList featuresList, FeatureTypes featureType) {
        Optional<Method> method = Arrays.stream(featuresList.getClass().getDeclaredMethods())
                .filter(m -> m.getName().equals(featureType.name()))
                .findAny();
        return method.map(m -> {
            Object[] args = Arrays.stream(m.getParameterTypes()).map(Defaults::defaultValue).toArray(Object[]::new);
            try {
                m.invoke(args);
                return true;
            } catch (DefaultImplementstion defaultImplementstion) {
                return false;
            } catch (Exception other) {
                return true;
            }
        }).orElse(false);
    }

}
