package org.gromovhotels.hotelchain.graphicsapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import net.rgielen.fxweaver.core.FxmlView;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;
import org.gromovhotels.hotelchain.utils.fx.table.DataProvider;
import org.gromovhotels.hotelchain.utils.fx.table.factories.BookingsTableRowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.DELETE;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.copyToClipboard;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.*;

@Component
@FxmlView
public class BookingsTableController extends BaseController {
    private static final KeyCombination combinationCtrlC = new KeyCodeCombination(C, CONTROL_DOWN);

    @Autowired private DataProvider tableDataProvider;

    @FXML private TableView<BookingRow> bookingsTable;

    @FXML public void initialize() {
        bookingsTable.setRowFactory(new BookingsTableRowFactory());
        synchronizeTable();
    }

    @FXML private void keyPressedInTable(KeyEvent keyEvent) {
        BookingRow bookingRow = bookingsTable.getSelectionModel().getSelectedItem();
        if (keyEvent.getCode() == DELETE && bookingRow != null) {
            raiseYesOrNoAlert("Вы, действительно, хотите удалить выбранную бронь?", () ->
                    eventPublisher.publishDeleteBookingEvent(bookingRow.getBookingId().toString())
            );
        }
        if (combinationCtrlC.match(keyEvent) && bookingRow != null) {
            copyToClipboard(bookingRow.getBookingId().toString());
        }
    }

    public void synchronizeTable() {
        bookingsTable.setItems(observableArrayList(tableDataProvider.currentBookingRecords()));
    }

    public BookingRow getBookingRow(@NonNull String uuid) {
        return bookingsTable.getItems().stream()
                .filter(bi -> bi.getBookingId().toString().equals(uuid))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Не найден запись в таблице"));
    }

}
