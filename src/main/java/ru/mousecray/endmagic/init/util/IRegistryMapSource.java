package ru.mousecray.endmagic.init.util;

import java.util.List;

public interface IRegistryMapSource<Key, Value> {

    HashMap<Key, Value> elemes();
}