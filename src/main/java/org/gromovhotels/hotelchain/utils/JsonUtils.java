package org.gromovhotels.hotelchain.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public final class JsonUtils {

    private static final ObjectMapper a = new ObjectMapper().enable(INDENT_OUTPUT).registerModule(new JavaTimeModule());

    @SneakyThrows
    public static <T> String toJsonString(T obj) {
        return a.writeValueAsString(obj);
    }

    public static <T> T fromJsonTo(Class<T> clazz, String content) {
        try {
            return a.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Не удалось распарсить файл", e);
        }
    }
}
