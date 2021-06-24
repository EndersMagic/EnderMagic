package ru.mousecray.endmagic.api.embook;

import java.util.Optional;

public class PageContainer {
    public Optional<PageContainer> left = Optional.empty();
    public final IPage page1;
    public final IPage page2;
    public Optional<PageContainer> right = Optional.empty();

    public PageContainer(IPage page1, IPage page2) {
        this.page1 = page1;
        this.page2 = page2;
    }
}
