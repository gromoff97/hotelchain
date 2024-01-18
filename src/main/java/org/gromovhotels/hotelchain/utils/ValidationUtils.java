package org.gromovhotels.hotelchain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public final class ValidationUtils {

    public static String validateString(Object obj) {
        if (obj instanceof String s) {
            return s;
        }
        throw new IllegalArgumentException("Объект должен быть строкой: '%s'".formatted(obj));
    }

    public static LocalDate validateIsAfterNow(LocalDate localDate) {
        LocalDate now = LocalDate.now();
        if (!localDate.isAfter(now)) {
            throw new IllegalArgumentException("Дата '%s' должны быть позже '%s'".formatted(localDate, now));
        }
        return localDate;
    }

    public static LocalDate validateLocalDate(String str) {
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Строка не является датой (yyyy-MM-dd): '%s'".formatted(str));
        }
    }

    public static UUID validateUUID(String str) {
        try {
            return UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Строка не является UUID: '%s'".formatted(str));
        }
    }

    public static int validateNonNegative(int val) {
        if (val < 0) {
            throw new IllegalArgumentException("Значение '%s' не должно быть отрицательным".formatted(val));
        }
        return val;
    }
}
