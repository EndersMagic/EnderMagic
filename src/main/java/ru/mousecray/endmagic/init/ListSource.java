package ru.mousecray.endmagic.init;

import java.util.List;

public class ListSource<A> implements IRegistrySource<A> {

    private final List<A> elemes;

    public ListSource(List<A> elemes) {
        this.elemes = elemes;
    }

    @Override
    public List<A> elemes() {
        return elemes;
    }
}
