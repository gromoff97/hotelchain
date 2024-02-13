package org.gromovhotels.hotelchain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public final class ValidationUtils {

    public static <T> T validateNotNull(T obj, String prefix) {
        if (obj == null) {
            throw new IllegalArgumentException("%s -> объект не должен отсутствовать или быть null".formatted(prefix));
        }
        return obj;
    }

    public static String validateString(Object obj, String prefix) {
        if (obj instanceof String s) {
            return s;
        }
        throw new IllegalArgumentException("%s -> объект должен быть строкой".formatted(prefix));
    }

    public static LocalDate validateIsAfterNow(LocalDate localDate, String prefix) {
        LocalDate now = LocalDate.now();
        if (!localDate.isAfter(now)) {
            throw new IllegalArgumentException("%s -> дата '%s' должны быть позже '%s'".formatted(prefix, localDate, now));
        }
        return localDate;
    }

    public static LocalDate validateLocalDate(String str, String prefix) {
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("%s -> строка не является датой (yyyy-MM-dd): '%s'".formatted(prefix, str));
        }
    }

    public static UUID validateUUID(String str, String prefix) {
        try {
            return UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("%s -> строка не является UUID: '%s'".formatted(prefix, str));
        }
    }

    public static int validateNonNegative(int val) {
        if (val < 0) {
            throw new IllegalArgumentException("Значение '%s' не должно быть отрицательным".formatted(val));
        }
        return val;
    }
}
