package org.gromovhotels.hotelchain.utils.fx.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class SceneSwitcher {

    @Autowired private FxWeaver fxWeaver;

    private final EnumMap<HotelChainScene, Scene> scenesMap;
    private Stage mainStage;

    public SceneSwitcher() {
        scenesMap = new EnumMap<>(HotelChainScene.class);
    }

    @SneakyThrows
    public void initWithMainStage(Stage stage) {
        if (this.mainStage != null) {
            throw new AssertionError("Main stage не должен был задаться два раза");
        }
        this.mainStage = stage;
        for (HotelChainScene value : HotelChainScene.values()) {
            Parent root = fxWeaver.loadView(value.getControllerClazz());
            scenesMap.put(value, new Scene(root));
        }
    }

    public void switchToScene(HotelChainScene hotelChainScene){
        mainStage.setScene(scenesMap.get(hotelChainScene));
    }
}
