package ru.mousecray.endmagic.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class GroupIterator<A> implements Iterator<List<A>> {
    private final Iterator<A> list;
    private final Integer max;
    private final Function<A, Integer> size;

    public GroupIterator(Iterator<A> list, Integer max, Function<A, Integer> size) {

        this.list = list;
        this.max = max;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return list.hasNext();
    }

    @Override
    public List<A> next() {
        List<A> acc = new ArrayList<>();
        Integer accSize = 0;
        while (accSize < max && list.hasNext()) {
            A next = list.next();
            acc.add(next);
            accSize += size.apply(next);
        }
        return acc;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
