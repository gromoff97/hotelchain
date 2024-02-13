package org.gromovhotels.hotelchain.graphicsapp.event.events.hotel;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public final class HotelCreationRequestedEvent extends ApplicationEvent {
    @Getter private final String hotelName;
    @Getter private final String hotelAddress;

    public HotelCreationRequestedEvent(Object source, String hotelName, String hotelAddress) {
        super(source);
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
    }
}
