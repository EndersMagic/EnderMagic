package ru.mousecray.endmagic.init.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

public class SourceComposition<A> implements IRegistrySource<A> {

    private final IRegistrySource<A> first;
    private final IRegistrySource<A> second;

    public SourceComposition(IRegistrySource<A> first, IRegistrySource<A> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public List<A> elemes() {
        return Lists.newArrayList(Iterables.concat(first.elemes(), second.elemes()));
    }
}
