package ru.mousecray.endmagic.init;

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
                .flatMap(field -> {
                    try {
                        A elem = (A) field.get(null);
                        return Stream.of(elem);
                    } catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
                        if (traceErrors)
                            e.printStackTrace();
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }
}
