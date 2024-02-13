package org.gromovhotels.hotelchain.graphicsapp.controllers;

import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.scene.HotelChainScene.HELP;

@Component
@FxmlView("/fxml/main-view.fxml")
public class MainController extends BaseController {

    @FXML private void helpButtonClicked() {
        sceneSwitcher.switchToScene(HELP);
    }

}
