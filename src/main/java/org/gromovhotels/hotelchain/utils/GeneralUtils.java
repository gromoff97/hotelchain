package org.gromovhotels.hotelchain.utils;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.TextStringBuilder;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;
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

    public static void copyToClipboard(@NonNull String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public static <T> Supplier<T> retryFunction(Supplier<T> supplier, int maxRetries) {
        return () -> {
            int retries = 0;
            while (retries < maxRetries) {
                try {
                    return supplier.get();
                } catch (Exception e) {
                    retries++;
                }
            }
            throw new IllegalStateException(String.format("Task failed after %s attempts", maxRetries));
        };
    }

    static <T> CompletableFuture<T> retryTask(Supplier<T> supplier, int maxRetries) {
        Supplier<T> retryableSupplier = retryFunction(supplier, maxRetries);
        return CompletableFuture.supplyAsync(retryableSupplier);
    }

    public static <T> String enumerated(List<T> list) {
        if (list.isEmpty()) {
            return "Пусто";
        }

        TextStringBuilder sb = new TextStringBuilder();
        ListIterator<T> it = list.listIterator();
        while (it.hasNext()) {
            sb.append("# ").append(it.nextIndex()).appendNewLine();
            sb.append(it.next());
            sb.append("-----").appendNewLine();
        }

        return sb.build();
    }
}
