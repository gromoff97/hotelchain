package org.gromovhotels.hotelchain.utils.fx.table.factories;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;

import java.util.function.Consumer;

import static org.gromovhotels.hotelchain.utils.GeneralUtils.copyToClipboard;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseBookingInfoAlert;


public class BookingsTableRowFactory implements Callback<TableView<BookingRow>, TableRow<BookingRow>> {

    @Override
    public TableRow<BookingRow> call(TableView<BookingRow> bookingsTable) {
        TableRow<BookingRow> row = new TableRow<>();
        row.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                if (mouseEvent.getClickCount() == 1 && row.isEmpty()) {
                    bookingsTable.getSelectionModel().clearSelection();
                }
                if (mouseEvent.getClickCount() == 2) {
                    BookingRow selectedBooking = bookingsTable.getSelectionModel().getSelectedItem();
                    if (selectedBooking != null) {
                        raiseBookingInfoAlert("Информация о бронировании", selectedBooking);
                    }
                }
            }

            if (mouseEvent.isSecondaryButtonDown() && mouseEvent.getClickCount() == 1) {
                BookingRow booking = bookingsTable.getSelectionModel().getSelectedItem();
                if (booking != null) {
                    row.setContextMenu(copyContextMenu(actionEventIfCopy -> copyToClipboard(booking.getBookingId().toString())));
                }
            }
        });
        return row;
    }

    private static ContextMenu copyContextMenu(Consumer<ActionEvent> doIfCopyClicked) {
        var contextMenu = new ContextMenu();
        MenuItem copyUuidItem = new MenuItem("Копировать UUID Бронирования");
        copyUuidItem.setOnAction(doIfCopyClicked::accept);
        contextMenu.getItems().add(copyUuidItem);
        return contextMenu;
    }
}
