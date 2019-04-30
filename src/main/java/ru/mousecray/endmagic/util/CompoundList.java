package ru.mousecray.endmagic.util;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CompoundList<A> implements List<A> {
    private final List<A> l1;
    private final List<A> l2;

    public CompoundList(List<A> l1, List<A> l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public int size() {
        return l1.size() + l2.size();
    }

    @Override
    public boolean isEmpty() {
        return l1.isEmpty() && l2.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return l1.contains(o) || l2.contains(o);
    }

    @Override
    public Iterator<A> iterator() {
        return new Iterator<A>() {
            Iterator<A> i1 = l1.iterator();
            Iterator<A> i2 = l2.iterator();

            @Override
            public boolean hasNext() {
                return i1.hasNext() || i2.hasNext();
            }

            @Override
            public A next() {
                return i1.hasNext() ? i1.next() : i2.next();
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException("CompoundList#toArray");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new NotImplementedException("CompoundList#toArray");
    }

    @Override
    public boolean add(A a) {
        return l2.add(a);
    }

    @Override
    public boolean remove(Object o) {
        return l1.remove(o) || l2.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException("CompoundList#containsAll");
    }

    @Override
    public boolean addAll(Collection<? extends A> c) {
        return l2.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends A> c) {
        throw new NotImplementedException("CompoundList#addAll");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return l1.removeAll(c) || l2.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException("CompoundList#retainAll");
    }

    @Override
    public void clear() {
        l1.clear();
        l2.clear();
    }

    @Override
    public A get(int index) {
        return index < l1.size() ? l1.get(index) : l2.get(index);
    }

    @Override
    public A set(int index, A element) {
        throw new NotImplementedException("CompoundList#set");
    }

    @Override
    public void add(int index, A element) {
        throw new NotImplementedException("CompoundList#add");
    }

    @Override
    public A remove(int index) {
        throw new NotImplementedException("CompoundList#remove");
    }

    @Override
    public int indexOf(Object o) {
        int i = l1.indexOf(o);
        return i == -1 ? l2.indexOf(o) : i;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = l2.lastIndexOf(o);
        return i == -1 ? l1.indexOf(o) : i;
    }

    @Override
    public ListIterator<A> listIterator() {
        return new ListIterator<A>() {
            ListIterator<A> i1 = l1.listIterator();
            ListIterator<A> i2 = l2.listIterator();

            @Override
            public boolean hasNext() {
                return i1.hasNext() || i2.hasNext();
            }

            @Override
            public A next() {
                return i1.hasNext() ? i1.next() : i2.next();
            }

            @Override
            public boolean hasPrevious() {
                return i1.hasPrevious() || i2.hasPrevious();
            }

            @Override
            public A previous() {
                return i2.hasPrevious() ? i2.previous() : i1.previous();
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
                throw new NotImplementedException("CompoundList#ListIterator#remove");
            }

            @Override
            public void set(A a) {
                throw new NotImplementedException("CompoundList#ListIterator#set");
            }

            @Override
            public void add(A a) {
                throw new NotImplementedException("CompoundList#ListIterator#add");
            }
        };
    }

    @Override
    public ListIterator<A> listIterator(int index) {
        throw new NotImplementedException("CompoundList#listIterator");
    }

    @Override
    public List<A> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException("CompoundList#subList");
    }
}
