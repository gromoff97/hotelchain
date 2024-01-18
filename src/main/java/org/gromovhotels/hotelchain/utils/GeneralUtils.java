package org.gromovhotels.hotelchain.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GeneralUtils {

    public static void times(int n, Runnable r) {
        for (int i = 0; i < n; i++) {
            r.run();
        }
    }

    public static <T> T findSingleOrThrow(Iterable<T> iterable, Predicate<T> predicate) {
        var all = CollectionUtils.select(iterable, predicate::test);
        if (all.size() != 1) {
            throw new IllegalArgumentException();
        }
        return all.iterator().next();
    }
}
