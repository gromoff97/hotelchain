package org.gromovhotels.hotelchain.graphicsapp.controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import org.gromovhotels.hotelchain.file.FileDataManipulationService;
import org.gromovhotels.hotelchain.graphicsapp.event.UiAppEventPublisher;
import org.gromovhotels.hotelchain.utils.fx.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static javafx.scene.input.KeyCode.ESCAPE;
import static org.gromovhotels.hotelchain.utils.FileUtils.DEFAULT_FILE;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.copyToClipboard;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseExitAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseInfoAlert;

@Component
public abstract class BaseController {

    @Autowired protected UiAppEventPublisher eventPublisher;

    @Autowired protected FileDataManipulationService fileDataManipulationService;

    @Autowired protected SceneSwitcher sceneSwitcher;

    @FXML protected void keyPressedInView(KeyEvent keyEvent) {
        if (keyEvent.getCode() == ESCAPE) {
            raiseExitAlert(() -> {
                fileDataManipulationService.setSaveOnExit(true);
                copyToClipboard(DEFAULT_FILE.toPath().toString());
                String message = "Текущее состоние будет сохранено. Путь скопирован в буфер обмена.";
                raiseInfoAlert("Выход", message, a -> {}, Platform::exit);
            }, Platform::exit);
        }
    }

}
