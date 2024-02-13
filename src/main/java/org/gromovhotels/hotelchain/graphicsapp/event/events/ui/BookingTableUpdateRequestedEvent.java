package org.gromovhotels.hotelchain.graphicsapp.event.events.ui;

import org.springframework.context.ApplicationEvent;

public final class BookingTableUpdateRequestedEvent extends ApplicationEvent {
    public BookingTableUpdateRequestedEvent(Object source) {
        super(source);
    }
}
