package ru.mousecray.endmagic.init.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassFieldSource<A> implements IRegistrySource<A> {
    public final Class sourceClass;
    private final boolean traceErrors;

    public ClassFieldSource(Class sourceClass, boolean traceErrors) {
        this.sourceClass = sourceClass;
        this.traceErrors = traceErrors;
    }

    public ClassFieldSource(Class sourceClass) {
        this(sourceClass, false);
    }

    @Override
    public List<A> elemes() {
        return Arrays.stream(sourceClass.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && field.getAnnotationsByType(SkipRegistry.class).length == 0)
                .flatMap(field -> {
                    try {
                        A elem = (A) field.get(null);
                        return Stream.of(elem);
                    } catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
                        if (traceErrors) {
                            System.out.println("Problem with field: " + field.getName());
                            e.printStackTrace();
                        }
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD})
    public @interface SkipRegistry {}
}
