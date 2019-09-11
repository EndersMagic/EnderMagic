package ru.mousecray.endmagic.api.embook;

import ru.mousecray.endmagic.api.embook.components.LinksComponent;
import ru.mousecray.endmagic.api.embook.pages.EmptyPage;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BookApi {
    public static final int pageWidth = 104;
    public static final int pageHeight = 147;

    static Map<String, Set<String>> categories = new HashMap<>();

    static Map<String, PageContainer> pageByName = new HashMap<>();

    private static boolean dirty = true;

    public static Map<String, PageContainer> getBookContent() {
        return pageByName;
    }

    private static PageContainer mainChapter;

    public static PageContainer mainChapter() {
        checkAndResolveDirty();
        return mainChapter;
    }

    private static void checkAndResolveDirty() {
        if (dirty) {

            for (Map.Entry<String, Set<String>> category : categories.entrySet())
                pageByName.put(category.getKey(), createPageContainerForLinks(category.getValue()));

            mainChapter = createPageContainerForLinks(categories.keySet());


            dirty = false;
        }
    }

    private static PageContainer createPageContainerForLinks(Set<String> strings) {
        return flatMapToPages(Stream.of(new LinksComponent(new ArrayList<>(strings))));
    }

    public static void addChapter(String category, String name, List<IChapterComponent> content) {
        addChapter(category, name, content.stream());
    }

    public static void addChapter(String category, String name, IChapterComponent... content) {
        addChapter(category, name, Stream.of(content));
    }

    public static void addChapter(String category, String name, Stream<IChapterComponent> content) {
        pageByName.put(name, flatMapToPages(content));
        categories.computeIfAbsent(category, __ -> new HashSet<>()).add(name);
        dirty = true;
    }

    private static PageContainer flatMapToPages(Stream<IChapterComponent> stream) {
        List<IPage> pages = stream.flatMap(i -> i.pages().stream()).collect(toList());

        if (pages.size() % 2 == 1)
            pages.add(new EmptyPage());

        if (pages.size() > 0) {
            List<PageContainer> groupedPairs = new ArrayList<>(pages.size() / 2);

            for (int i = 0; i < pages.size(); i += 2)
                groupedPairs.add(new PageContainer(pages.get(i), pages.get(i + 1)));

            Function<Integer, Optional<PageContainer>> getMaybePage = lift(groupedPairs::get);

            for (int i = 0; i < groupedPairs.size(); i++) {
                groupedPairs.get(i).left = getMaybePage.apply(i - 1);
                groupedPairs.get(i).right = getMaybePage.apply(i + 1);
            }
            return groupedPairs.get(0);
        } else
            return null;
    }

    private static <A, B> Function<A, Optional<B>> lift(Function<A, B> partialFunction) {
        return x -> {
            try {
                return Optional.ofNullable(partialFunction.apply(x));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
}
