package org.gromovhotels.hotelchain.graphicsapp.event.listeners.booking;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingRemovalRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseBookingInfoAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;

@Component
public class BookingRemovalRequestedListener extends AbstractApplicationListener<BookingRemovalRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull BookingRemovalRequestedEvent event) {
        String uuid = event.getBookingId();
        Try.runRunnable(() -> bookingService.deleteBookingById(uuid))
                .onSuccess(unused -> {
                    BookingRow bookingRow = tableController.getBookingRow(uuid);
                    eventPublisher.publishBookingTableUpdateRequestedEvent();
                    managementController.getDeletedBookingTextField().clear();
                    raiseBookingInfoAlert("Удалено бронирование", bookingRow);
                })
                .onFailure(throwable ->
                        raiseErrorAlert("Ошибка удаления бронирования: %s".formatted(throwable.getMessage()))
                );
    }

}
