package ru.mousecray.endmagic.init.util;

import java.util.List;

public interface IRegistrySource<A> {

    List<A> elemes();

    default IRegistrySource<A> and(IRegistrySource<A> b) {
        return new SourceComposition<>(this, b);
    }

}
