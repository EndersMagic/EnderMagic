package ru.mousecray.endmagic.api.embook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.api.embook.pages.MainPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class BookApi {
    public static final int pageWidth = 256/2;
    public static final int pageHeight = 192;

    static Map<String, PageContainer> book = new HashMap<String, PageContainer>() {
        @Override
        public PageContainer put(String key, PageContainer value) {
            dirty = true;
            return super.put(key, value);
        }
    };

    private static boolean dirty = true;

    private static ImmutableMap<String, PageContainer> immutableMap;

    public static ImmutableMap<String, PageContainer> getBookContent() {
        checkAndResolveDirty();
        return immutableMap;
    }

    private static PageContainer mainPage;

    public static PageContainer mainPage() {
        checkAndResolveDirty();
        return mainPage;
    }

    private static void checkAndResolveDirty() {
        if (dirty) {
            immutableMap = ImmutableMap.copyOf(book);
            mainPage = new PageContainer(new MainPage(immutableMap));
            dirty = false;
        }
    }

    public static void addChapter(String name, List<IChapterComponent> content) {
        Stream<IChapterComponent> stream = content.stream();
        book.put(name, flatMapToPages(stream));
    }

    public static void addChapter(String name, IChapterComponent... content) {
        book.put(name, flatMapToPages(Stream.of(content)));
    }

    private static PageContainer flatMapToPages(Stream<IChapterComponent> stream) {
        ImmutableList<PageContainer> containers = stream
                .flatMap(i -> i.pages().stream())
                .map(PageContainer::new).collect(toImmutableList());
        if (containers.size() > 0) {
            for (int i = 1; i < containers.size() - 1; i++) {
                containers.get(i).left = containers.get(i - 1);
                containers.get(i).right = containers.get(i + 1);
            }
            if (containers.size() > 1) {
                containers.get(0).right = containers.get(1);
                containers.get(containers.size() - 1).left = containers.get(containers.size() - 2);
            }
            return containers.get(0);
        } else
            return null;
    }
}
