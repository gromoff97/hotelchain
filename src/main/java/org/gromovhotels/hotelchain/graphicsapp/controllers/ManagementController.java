package org.gromovhotels.hotelchain.graphicsapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.gromovhotels.hotelchain.guest.HotelGuestRepository;
import org.gromovhotels.hotelchain.utils.fx.models.HotelComboBoxItem;
import org.gromovhotels.hotelchain.utils.fx.models.RoomComboBoxItem;
import org.gromovhotels.hotelchain.utils.fx.table.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static javafx.collections.FXCollections.observableList;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.stripToNull;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseScrollableListInfoAlert;

@Component
@FxmlView
public class ManagementController extends BaseController {

    @Autowired
    private DataProvider dataProvider;

    // Секция "бронирования"
    @FXML private TextField guestNameTextField;
    @FXML private TextField passportNumberTextField;
    @FXML private ComboBox<HotelComboBoxItem> hotelComboBox;
    @FXML private ComboBox<RoomComboBoxItem> roomComboBox;
    @FXML private DatePicker checkInDateField;
    @FXML private DatePicker checkOutDateField;
    @FXML private CheckBox isPaidCheckbox;
    @FXML @Getter private TextField payBookingTextField;
    @FXML @Getter private TextField deletedBookingTextField;

    @FXML private void createBookingButtonClicked() {
        String guestName = guestNameTextField.getText().isBlank() ? null : normalizeSpace(guestNameTextField.getText().strip());
        String passportNumber = passportNumberTextField.getText().isBlank() ? null : normalizeSpace(passportNumberTextField.getText().strip());
        String hotelRoomId = ofNullable(roomComboBox.getValue()).map(RoomComboBoxItem::uuid).map(UUID::toString).orElse(null);
        String checkIn = ofNullable(checkInDateField.getValue()).map(LocalDate::toString).orElse(null);
        String checkOut = ofNullable(checkOutDateField.getValue()).map(LocalDate::toString).orElse(null);
        boolean isPaid = isPaidCheckbox.isSelected();
        eventPublisher.publishCreateBookingEvent(guestName, passportNumber, hotelRoomId, checkIn, checkOut, isPaid);
    }

    @FXML private void payBookingButtonClicked() {
        String uuid = payBookingTextField.getText().strip();
        eventPublisher.publishPayBookingEvent(uuid);
    }

    @FXML private void deleteBookingButtonClicked() {
        String uuid = deletedBookingTextField.getText().strip();
        eventPublisher.publishDeleteBookingEvent(uuid);
    }

    @FXML public void comboBoxHotelSelected() {
        if (hotelComboBox.getValue() == null) {
            setComboboxValues(roomComboBox, emptyList());
            return;
        }
        UUID selectedHotelId = hotelComboBox.getValue().hotelUuid();
        setComboboxValues(roomComboBox, dataProvider.roomsForHotel(selectedHotelId));
    }


    // Секция "Комнаты"
    @FXML private TextField hotelCreationNameTextField;
    @FXML private TextField hotelCreationAddressTextField;
    @FXML private ComboBox<HotelComboBoxItem> hotelRemovalComboBox;

    @FXML public void createHotelButtonClicked() {
        String hotelName = stripToNull(hotelCreationNameTextField.getText());
        String hotelAddress = stripToNull(hotelCreationAddressTextField.getText());
        eventPublisher.publishCreateHotelEvent(hotelName, hotelAddress);
    }

    @FXML public void removeHotelButtonClicked() {
        String hotelId = ofNullable(hotelRemovalComboBox.getValue()).map(HotelComboBoxItem::hotelUuid).map(UUID::toString).orElse(null);
        eventPublisher.publishHotelRemovalEvent(hotelId);
    }

    // Секция "Гости"
    @FXML private TextField hotelGuestCreationFullNameTextField;
    @FXML private TextField hotelGuestCreationPassportNumberTextField;

    @FXML private void createHotelGuestButtonClicked() {
        String guestFullName = stripToNull(hotelGuestCreationFullNameTextField.getText());
        String guestPassportNumber = stripToNull(hotelGuestCreationPassportNumberTextField.getText());
        eventPublisher.publishCreateHotelGuestEvent(guestFullName, guestPassportNumber);
    }

    @FXML private void pollGuestButtonClicked() {
        eventPublisher.publishPollHotelGuestEvent();
    }

    @Autowired
    private HotelGuestRepository hotelGuestRepository;

    @FXML private void showGuestsButtonClicked() {
        raiseScrollableListInfoAlert("Список гостей", hotelGuestRepository.getHotelGuests());
    }

    // Секция "Комнаты"
    @FXML private ComboBox<HotelComboBoxItem> createRoomHotelComboBox;
    @FXML private TextField createRoomRoomTextField;
    @FXML private ComboBox<HotelComboBoxItem> removeRoomHotelComboBox;
    @FXML private ComboBox<RoomComboBoxItem> removeRoomRoomComboBox;

    @FXML private void removeRoomHotelComboBoxChanged() {
        if (removeRoomHotelComboBox.getValue() == null) {
            setComboboxValues(removeRoomRoomComboBox, emptyList());
            return;
        }
        UUID selectedHotelId = removeRoomHotelComboBox.getValue().hotelUuid();
        setComboboxValues(removeRoomRoomComboBox, dataProvider.roomsForHotel(selectedHotelId));
    }

    @FXML private void createRoomButtonClicked() {
        String hotelId = Optional.ofNullable(createRoomHotelComboBox.getValue()).map(HotelComboBoxItem::hotelUuid).map(UUID::toString).orElse(null);
        String roomType = stripToNull(createRoomRoomTextField.getText());
        eventPublisher.publishCreateRoomEvent(hotelId, roomType);
    }

    @FXML private void deleteRoomButtonClicked() {
        String roomUuid = Optional.ofNullable(removeRoomRoomComboBox.getValue())
                .map(RoomComboBoxItem::uuid)
                .map(UUID::toString)
                .orElse(null);
        eventPublisher.publishDeleteRoomEvent(roomUuid);
    }

    public void synchronizeHotelsComboboxes() {
        List<HotelComboBoxItem> currentHotelsItems = dataProvider.currentHotelsItems();
        setComboboxValues(hotelComboBox, currentHotelsItems);
        setComboboxValues(hotelRemovalComboBox, currentHotelsItems);
        setComboboxValues(createRoomHotelComboBox, currentHotelsItems);
        setComboboxValues(removeRoomHotelComboBox, currentHotelsItems);
    }

    public void initialize() {
        synchronizeHotelsComboboxes();
    }

    private static <T> void setComboboxValues(ComboBox<T> combobox, List<T> values) {
        combobox.getItems().clear();
        combobox.getItems().addAll(observableList(values));
    }
}
