package org.gromovhotels.hotelchain.graphicsapp.controllers;

import io.vavr.control.Try;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.FileChooserUtils.chooseFile;
import static org.gromovhotels.hotelchain.utils.FileChooserUtils.saveFile;
import static org.gromovhotels.hotelchain.utils.FileUtils.deleteFile;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.copyToClipboard;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseInfoAlert;

@Component
@FxmlView
public class ImportExportController extends BaseController {

    @FXML private void importButtonClicked() {
        chooseFile("Выберите файл для импорта").ifPresent(chosenFile -> {
            Try.runRunnable(() -> fileDataManipulationService.importApplicationStateFrom(chosenFile)).onSuccess(unused -> {
                copyToClipboard(chosenFile.toPath().toString());
                raiseInfoAlert("Импорт", "Успешно импортировано (путь скопирован в буфер)");
                eventPublisher.publishBookingTableUpdateRequestedEvent();
                eventPublisher.publishHotelComboboxUpdateRequestedEvent();
            }).onFailure(throwable -> raiseInfoAlert("Импорт", "Ошибка импорта: " + throwable.getMessage()));
        });
    }

    @FXML private void exportButtonClicked() {
        saveFile("Выберите место для экспорта").ifPresent(file -> {
            if (file.exists()) deleteFile(file);
            fileDataManipulationService.exportCurrentApplicationStateTo(file);
            copyToClipboard(file.toPath().toString());
            raiseInfoAlert("Экспорт", "Успешно экспортировано (путь скопирован в буфер)");
        });
    }
}
