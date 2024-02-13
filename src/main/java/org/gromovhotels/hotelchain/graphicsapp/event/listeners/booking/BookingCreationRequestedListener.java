package org.gromovhotels.hotelchain.graphicsapp.event.listeners.booking;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingCreationRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.GeneralUtils.retryFunction;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseBookingInfoAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;

@Component
public class BookingCreationRequestedListener extends AbstractApplicationListener<BookingCreationRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull BookingCreationRequestedEvent event) {
        Try.ofCallable(() -> bookingService.createBooking(event)).onSuccess(createdBooking -> {
                    eventPublisher.publishBookingTableUpdateRequestedEvent();
                    BookingRow bookingInfo = retryFunction(() -> tableController.getBookingRow(createdBooking.id().toString()), 100).get();
                    raiseBookingInfoAlert("Создано бронирование", bookingInfo);
                })
                .onFailure(thr -> raiseErrorAlert("Ошибка создания бронирования: %s".formatted(thr.getMessage())));
    }
}
