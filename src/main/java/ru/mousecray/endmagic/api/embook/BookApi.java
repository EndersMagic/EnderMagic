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

    static Map<Object, PageContainer> links = new HashMap<>();

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
                pageByName.put(category.getKey(), createPageContainerForLinks(category.getValue(), category.getKey()));

            mainChapter = createPageContainerForLinks(categories.keySet(), "book.main_page_title");


            dirty = false;
        }
    }

    private static PageContainer createPageContainerForLinks(Set<String> strings, String title) {
        return flatMapToPages(Stream.of(new LinksComponent(new ArrayList<>(strings), title))).get(0);
    }

    public static void addChapter(String category, String name, List<IChapterComponent> content) {
        addChapter(category, name, content.stream());
    }

    public static void addChapter(String category, String name, IChapterComponent... content) {
        addChapter(category, name, Stream.of(content));
    }

    public static void addChapter(String category, String name, Stream<IChapterComponent> content) {
        List<PageContainer> value = flatMapToPages(content);
        value.forEach(pageContainer -> {
            tryToRegisterLinkLocation(pageContainer, pageContainer.page1);
            tryToRegisterLinkLocation(pageContainer, pageContainer.page2);
        });
        pageByName.put(name, value.get(0));
        categories.computeIfAbsent(category, __ -> new HashSet<>()).add(name);
        dirty = true;
    }

    private static void tryToRegisterLinkLocation(PageContainer pageContainer, IPage page1) {
        if (page1 instanceof ILinkLocation)
            links.put(((ILinkLocation) page1).linkObject(), pageContainer);
    }

    private static List<PageContainer> flatMapToPages(Stream<IChapterComponent> stream) {
        List<IPage> pages = stream.flatMap(i -> i.pages().stream()).collect(toList());

        if (pages.size() % 2 == 1)
            pages.add(new EmptyPage());

        if (pages.isEmpty()) {
            pages.add(new EmptyPage());
            pages.add(new EmptyPage());
        }

        List<PageContainer> groupedPairs = new ArrayList<>(pages.size() / 2);

        for (int i = 0; i < pages.size(); i += 2)
            groupedPairs.add(new PageContainer(pages.get(i), pages.get(i + 1)));

        Function<Integer, Optional<PageContainer>> getMaybePage = lift(groupedPairs::get);

        for (int i = 0; i < groupedPairs.size(); i++) {
            groupedPairs.get(i).left = getMaybePage.apply(i - 1);
            groupedPairs.get(i).right = getMaybePage.apply(i + 1);
        }
        return groupedPairs;
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

    public static PageContainer findLink(Object linkObject) {
        return links.get(linkObject);
    }
}
