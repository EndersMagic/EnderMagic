package ru.mousecray.endmagic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public class GroupIterator<A> implements ListIterator<List<A>> {
    private final ListIterator<A> list;
    private final Integer max;
    private final Function<A, Integer> size;

    public GroupIterator(ListIterator<A> list, Integer max, Function<A, Integer> size) {
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
        List<A> acc = new ArrayList<>(10);
        Integer accSize = 0;


        while (accSize < max && list.hasNext()) {
            A next = list.next();
            acc.add(next);
            accSize += size.apply(next);
        }

        if (accSize > max && list.hasPrevious()) {
            acc.remove(acc.size() - 1);
            if (!acc.isEmpty())
                list.previous();
        }

        return acc;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public List<A> previous() {
        return null;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public int previousIndex() {
        return 0;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(List<A> as) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(List<A> as) {
        throw new UnsupportedOperationException();
    }
}
