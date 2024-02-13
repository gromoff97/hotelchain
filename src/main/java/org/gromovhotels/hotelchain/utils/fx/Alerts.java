package org.gromovhotels.hotelchain.utils.fx;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import lombok.NonNull;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.room.HotelRoom;
import org.gromovhotels.hotelchain.utils.GeneralUtils;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static javafx.scene.control.Alert.AlertType.*;
import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.OK;
import static javafx.stage.StageStyle.UNDECORATED;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.enumerated;

public final class Alerts {

    public static void raiseRoomInfoAlert(String headerText, HotelRoom room) {
        raiseInfoAlert(headerText, room.toString(), alert -> alert.getDialogPane().setPrefSize(400, 275), () -> {});
    }

    public static void raiseGuestInfoAlert(String headerText, HotelGuest hotelGuest) {
        raiseInfoAlert(headerText, hotelGuest.toString(), alert -> alert.getDialogPane().setPrefSize(400, 275), () -> {});
    }

    public static void raiseHotelInfoAlert(String headerText, Hotel hotel) {
        raiseInfoAlert(headerText, hotel.toString(), alert -> alert.getDialogPane().setPrefSize(400, 275), () -> {});
    }

    public static void raiseBookingInfoAlert(@NonNull String headerText, @NonNull BookingRow bookingRow) {
        raiseInfoAlert(headerText, bookingRow.toString(), alert -> alert.getDialogPane().setPrefSize(400, 275), () -> {});
    }

    public static void raiseYesOrNoAlert(@NonNull String questionText, @NonNull Runnable actionIfYes) {
        Alert alert = new Alert(CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(OK)).setText("Да");
        ((Button) alert.getDialogPane().lookupButton(CANCEL)).setText("Нет");
        alert.initStyle(UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setContentText(questionText);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == OK){
                actionIfYes.run();
            }
        });
    }

    public static void raiseInfoAlert(@NonNull String headerText, @NonNull String info, @NonNull Consumer<Alert> changer, @NonNull Runnable ifOkClickedAction) {
        Alert alert = new Alert(INFORMATION);
        alert.initStyle(UNDECORATED);
        alert.setHeaderText(headerText);
        alert.setTitle(null);
        alert.getDialogPane().setContent(new Label(info));
        changer.accept(alert);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == OK){
                ifOkClickedAction.run();
            }
        });
    }

    public static void raiseInfoAlert(@NonNull String headerText, @NonNull String info) {
        raiseInfoAlert(headerText, info, alert -> {}, () -> {});
    }

    public static <T> void raiseScrollableListInfoAlert(@NonNull String headerText, @NonNull List<T> list) {
        raiseInfoAlert(headerText, enumerated(list), alert -> {
            TextArea area = new TextArea(enumerated(list));
            area.setWrapText(true);
            area.setEditable(false);

            alert.getDialogPane().setContent(area);
            alert.setResizable(true);
        }, () -> {});
    }

    public static void raiseExitAlert(Runnable checkBoxIsSelectedAction, Runnable yesClickedAction) {
        createConfirmationAlertWithOptOut(
                "Выход",
                "Уверены, что хотите выйти?",
                "Сохранить текущее состояние",
                checkBoxIsSelectedAction, yesClickedAction);
    }


    // https://stackoverflow.com/a/36949596/8505330
    public static void createConfirmationAlertWithOptOut(String title, String message, String optOutMessage, Runnable ifSelectedAction, Runnable ifYesAction) {
        Alert alert = new Alert(CONFIRMATION);
        alert.initStyle(UNDECORATED);

        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();

        AtomicBoolean atomicBoolean = new AtomicBoolean();

        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                CheckBox optOut = new CheckBox();
                optOut.setText(optOutMessage);
                optOut.setOnAction(e -> atomicBoolean.set(optOut.isSelected()));
                return optOut;
            }
        });
        alert.getDialogPane().getButtonTypes().addAll(OK, CANCEL);
        ((Button) alert.getDialogPane().lookupButton(OK)).setText("Да");
        ((Button) alert.getDialogPane().lookupButton(CANCEL)).setText("Нет");
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        alert.getDialogPane().setGraphic(graphic);
        alert.getDialogPane().setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(null);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == OK) {
                if (atomicBoolean.get()) {
                    ifSelectedAction.run();
                }
                ifYesAction.run();
            }
        });
    }

    public static void raiseErrorAlert(String errorText) {
        Alert alert = new Alert(ERROR);
        alert.initStyle(UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle(null);

        Label label = new Label(errorText);
        label.setWrapText(true);

        // Установка максимальной ширины текстового блока
        label.setMaxWidth(Double.MAX_VALUE);

        alert.getDialogPane().setContent(label);

        alert.show();
    }

}
