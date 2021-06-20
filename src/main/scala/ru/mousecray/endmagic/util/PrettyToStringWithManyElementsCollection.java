package ru.mousecray.endmagic.util;

import java.util.Collection;
import java.util.Iterator;

public class PrettyToStringWithManyElementsCollection<E> implements Collection<E> {

    private final Collection<E> base;
    public static int maxFullStringRepresentLength = 50;

    public PrettyToStringWithManyElementsCollection(Collection<E> base) {
        this.base = base;
    }

    @Override
    public String toString() {
        String r = super.toString();
        if (r.length() <= maxFullStringRepresentLength)
            return r;
        else {
            String addition = "...]";
            return r.substring(0, maxFullStringRepresentLength - addition.length()) + addition;
        }
    }

    @Override
    public int size() {
        return base.size();
    }

    @Override
    public boolean isEmpty() {
        return base.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return base.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return base.iterator();
    }

    @Override
    public Object[] toArray() {
        return base.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return base.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return base.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return base.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return base.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return base.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return base.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return base.retainAll(c);
    }

    @Override
    public void clear() {
        base.clear();
    }
}
