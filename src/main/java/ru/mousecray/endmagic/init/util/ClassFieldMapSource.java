package ru.mousecray.endmagic.init.util;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassFieldMapSource<Key, Value> implements IRegistrySource<Value> {
    public final Class sourceClass;
    private final boolean traceErrors;

    public ClassFieldMapSource(Class sourceClass, boolean traceErrors) {
        this.sourceClass = sourceClass;
        this.traceErrors = traceErrors;
    }

    public ClassFieldMapSource(Class sourceClass) {
        this(sourceClass, false);
    }

	@Override
	public HashMap<Key, Value> elemes() {
        return Arrays.stream(sourceClass.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()))
                .flatMap(field -> {
                    try {
                        A elem = (A) field.get(null);
                        return Stream.of(elem);
                    } catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
                        if (traceErrors) {
                            System.out.println("Problem with fieled: "+field.getName());
                            e.printStackTrace();
                        }
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
	}
}