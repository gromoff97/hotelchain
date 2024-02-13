package org.gromovhotels.hotelchain.utils.fx;

import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.gromovhotels.hotelchain.utils.fx.StageProvider.deafultStageSupplier;

public final class UiAppExecutor {

    public static void executeGraphicsApplicationDrivenBy(Application javaFxApplication) {
        Platform.startup(startApplication(javaFxApplication));
    }

    private static Runnable startApplication(Application graphicsApp) {
        return () -> {
            try {
                graphicsApp.start(deafultStageSupplier.get());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
