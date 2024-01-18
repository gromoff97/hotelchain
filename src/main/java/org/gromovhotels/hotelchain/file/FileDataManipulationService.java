package org.gromovhotels.hotelchain.file;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.gromovhotels.hotelchain.utils.FileUtils.*;

/**
 * Сервис для взаимодействия приложения с файловой системой.
 * Предназначен для загрузки и сохранения состояния приложения.
 */
@Service
public final class FileDataManipulationService {

    @Autowired private ApplicationStateRepository applicationStateRepository;

    @PostConstruct
    private void importApplicationStateFromDefaultFile() {
        if (DEFAULT_FILE.exists()) {
            importApplicationStateFrom(DEFAULT_FILE);
            return;
        }
        importEmptyApplicationState();
    }

    @PreDestroy
    private void exportCurrentApplicationStateToDefaultFile() throws IOException {
        Files.deleteIfExists(DEFAULT_FILE.toPath());
        exportCurrentApplicationStateTo(DEFAULT_FILE);
    }

    public void importApplicationStateFrom(String filePath) {
        importApplicationStateFrom(Paths.get(filePath).toFile());
    }

    public void exportCurrentApplicationStateTo(String filePath) {
        exportCurrentApplicationStateTo(Paths.get(filePath).toFile());
    }

    public void importApplicationStateFrom(File file) {
        ApplicationState appState = readApplicationStateFrom(file);
        applicationStateRepository.applyApplicationStateToApp(appState);
    }

    public void importEmptyApplicationState() {
        applicationStateRepository.applyApplicationStateToApp(ApplicationState.empty());
    }

    public void exportCurrentApplicationStateTo(File file) {
        if (file.exists()) {
            throw new IllegalArgumentException("Файл уже существует -> " + file);
        }
        file.getParentFile().mkdirs();
        writeToFile(file, applicationStateRepository.getCurrentApplicationState());
    }

    private static ApplicationState readApplicationStateFrom(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Файла не существует -> " + file);
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Файл не должен быть директорией -> " + file);
        }
        return readFileAs(ApplicationState.class, file);
    }
}
