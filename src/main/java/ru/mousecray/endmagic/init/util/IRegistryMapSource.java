package ru.mousecray.endmagic.init.util;

import java.util.Map;

public interface IRegistryMapSource<Key, Value> {

    Map<Key, Value> elemes();
}