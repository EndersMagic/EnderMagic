package ru.mousecray.endmagic.api.embook;

import com.google.common.collect.ImmutableMap;
import ru.mousecray.endmagic.api.embook.pages.EmptyPage;
import ru.mousecray.endmagic.api.embook.pages.MainPage;

import java.util.*;
import java.util.function.Function;
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
        book.put(name, flatMapToPages(content.stream()));
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
