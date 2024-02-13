package org.gromovhotels.hotelchain.graphicsapp.controllers;

import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.scene.HotelChainScene.MAIN;

@Component
@FxmlView("/fxml/help-view.fxml")
public class HelpController extends BaseController {

    @FXML
    private void backButtonClicked() {
        sceneSwitcher.switchToScene(MAIN);
    }

}
