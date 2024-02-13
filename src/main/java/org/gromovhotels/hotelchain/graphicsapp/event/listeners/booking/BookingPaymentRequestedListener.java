package org.gromovhotels.hotelchain.graphicsapp.event.listeners.booking;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingPaymentRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseBookingInfoAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;

@Component
public class BookingPaymentRequestedListener extends AbstractApplicationListener<BookingPaymentRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull BookingPaymentRequestedEvent event) {
        String uuid = event.getBookingId();
        Try.runRunnable(() -> bookingService.payBooking(uuid)).onSuccess(unused -> {
                    BookingRow bookingInfo = tableController.getBookingRow(uuid).withPaymentDate(LocalDate.now());
                    eventPublisher.publishBookingTableUpdateRequestedEvent();
                    managementController.getPayBookingTextField().clear();
                    raiseBookingInfoAlert("Оплачено бронирование", bookingInfo);
                })
                .onFailure(thr -> raiseErrorAlert("Ошибка удаления бронирования: %s".formatted(thr.getMessage())));
    }
}
