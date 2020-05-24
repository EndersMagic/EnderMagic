package ru.mousecray.endmagic.util;

@FunctionalInterface
public interface SeparatingConsumer<T> {
    void accept(T cons, T consC);
}