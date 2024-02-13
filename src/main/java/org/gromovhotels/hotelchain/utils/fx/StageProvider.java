package org.gromovhotels.hotelchain.utils.fx;

import javafx.stage.Stage;

import java.util.function.Supplier;

import static javafx.stage.StageStyle.UNDECORATED;

public final class StageProvider {

    public static Supplier<Stage> deafultStageSupplier = () -> {
        var stage = new Stage();
        stage.initStyle(UNDECORATED);
        return stage;
    };
}
