package org.gromovhotels.hotelchain.graphicsapp.event.events.ui;

import org.springframework.context.ApplicationEvent;

public class HotelComboboxUpdateRequestedEvent extends ApplicationEvent {
    public HotelComboboxUpdateRequestedEvent(Object source) {
        super(source);
    }
}
