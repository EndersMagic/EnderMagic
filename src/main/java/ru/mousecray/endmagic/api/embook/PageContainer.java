package ru.mousecray.endmagic.api.embook;

public class PageContainer {
    public PageContainer left;
    public final IPage page;
    public PageContainer right;

    public PageContainer(PageContainer left, IPage page, PageContainer right) {
        this.left = left;
        this.page = page;
        this.right = right;
    }

    public PageContainer(IPage page) {
        this.page = page;
    }
}
