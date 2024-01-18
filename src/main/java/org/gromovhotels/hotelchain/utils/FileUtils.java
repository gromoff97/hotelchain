package org.gromovhotels.hotelchain.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.getProperty;
import static org.gromovhotels.hotelchain.utils.JsonUtils.fromJsonTo;
import static org.gromovhotels.hotelchain.utils.JsonUtils.toJsonString;

public class FileUtils {
    private static final Path TEMP_DIR_PATH = Paths.get(getProperty("user.home")).resolve("hotelchain");
    public static final File DEFAULT_FILE = TEMP_DIR_PATH.resolve("saved_data.json").toFile();

    @SneakyThrows
    public static void createDefaultFile() {
        Files.createFile(DEFAULT_FILE.toPath());
    }

    @SneakyThrows
    public static void deleteDefaultFileIfExists() {
        Files.deleteIfExists(DEFAULT_FILE.toPath());
    }

    @SneakyThrows
    private static String readDefaultFile() {
        return Files.readString(DEFAULT_FILE.toPath());
    }

    @SneakyThrows
    public static <T> T readFileAs(Class<T> clazz, File file) {
        return fromJsonTo(clazz, Files.readString(file.toPath()));
    }

    @SneakyThrows
    public static <T> T readDefaultFileAs(Class<T> clazz) {
        String str = readDefaultFile();
        return fromJsonTo(clazz, str);
    }

    @SneakyThrows
    public static <T> void writeToFile(File file, T obj) {
        writeToFile(file, toJsonString(obj));
    }

    @SneakyThrows
    private static void writeToFile(File file, String content) {
        try (var w = new PrintWriter(file)) {
            w.print(content);
        }
    }
}
