package org.gromovhotels.hotelchain.graphicsapp.event.events.booking;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public final class BookingPaymentRequestedEvent extends ApplicationEvent {
    @Getter private final String bookingId;

    public BookingPaymentRequestedEvent(Object source, String bookingId) {
        super(source);
        this.bookingId = bookingId;
    }
}
