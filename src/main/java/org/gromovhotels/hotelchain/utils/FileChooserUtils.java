package org.gromovhotels.hotelchain.utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public final class FileChooserUtils {

    public static Optional<File> chooseFile(String title) {
        return Optional.ofNullable(defaultFileChooser(title).showOpenDialog(null));
    }

    public static Optional<File> saveFile(String title) {
        return Optional.ofNullable(defaultFileChooser(title).showSaveDialog(null));
    }

    private static FileChooser defaultFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File recordsDir = new File(System.getProperty("user.home"), "hotelchain");
        if (!recordsDir.exists()) recordsDir.mkdirs();
        fileChooser.setInitialDirectory(recordsDir);

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Any file", "*.*"));

        return fileChooser;
    }
}
