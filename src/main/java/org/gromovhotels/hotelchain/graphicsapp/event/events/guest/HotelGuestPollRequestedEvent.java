package org.gromovhotels.hotelchain.graphicsapp.event.events.guest;

import org.springframework.context.ApplicationEvent;

public class HotelGuestPollRequestedEvent extends ApplicationEvent {
    public HotelGuestPollRequestedEvent(Object source) {
        super(source);
    }
}
