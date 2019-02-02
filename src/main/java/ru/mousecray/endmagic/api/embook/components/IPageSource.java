package ru.mousecray.endmagic.api.embook.components;

import ru.mousecray.endmagic.api.embook.Page;

import java.util.List;
import java.util.stream.Stream;

public interface IPageSource {
    public Stream<Page> build();
}
