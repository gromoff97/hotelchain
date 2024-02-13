package org.gromovhotels.hotelchain.graphicsapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.gromovhotels.hotelchain.utils.fx.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.scene.HotelChainScene.MAIN;

@Component
public class JavaFxApp extends Application {

    @Autowired private SceneSwitcher sceneSwitcher;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Гостиничная Сеть");
        sceneSwitcher.initWithMainStage(stage);
        sceneSwitcher.switchToScene(MAIN);
        stage.show();
    }
}
