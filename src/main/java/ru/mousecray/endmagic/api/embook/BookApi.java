package ru.mousecray.endmagic.api.embook;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.api.embook.pages.EmptyPage;
import ru.mousecray.endmagic.api.embook.pages.MainPage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookApi {
    public static final int pageWidth = 104;
    public static final int pageHeight = 147;

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
            mainPage = new PageContainer(new MainPage(immutableMap), new EmptyPage());
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
        List<IPage> pages = stream.flatMap(i -> i.pages().stream()).collect(Collectors.toList());

        if (pages.size() % 2 == 1)
            pages.add(new EmptyPage());

        if (pages.size() > 0) {
            List<PageContainer> groupedPairs = new ArrayList<>(pages.size() / 2);

            for (int i = 0; i < pages.size(); i += 2)
                groupedPairs.add(new PageContainer(pages.get(i), pages.get(i + 1)));

            for (int i = 1; i < groupedPairs.size() - 1; i++) {
                groupedPairs.get(i).left = Optional.ofNullable(groupedPairs.get(i - 1));
                groupedPairs.get(i).right = Optional.ofNullable(groupedPairs.get(i + 1));
            }

            if (groupedPairs.size() > 1) {
                groupedPairs.get(0).right = Optional.ofNullable(groupedPairs.get(1));
                groupedPairs.get(groupedPairs.size() - 1).left = Optional.ofNullable(groupedPairs.get(groupedPairs.size() - 2));
            }
            return groupedPairs.get(0);
        } else
            return null;
    }
}
