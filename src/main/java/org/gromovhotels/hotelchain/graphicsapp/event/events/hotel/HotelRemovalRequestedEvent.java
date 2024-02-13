package org.gromovhotels.hotelchain.graphicsapp.event.events.hotel;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public final class HotelRemovalRequestedEvent extends ApplicationEvent {

    @Getter private final String hotelUuid;

    public HotelRemovalRequestedEvent(Object source, String hotelUuid) {
        super(source);
        this.hotelUuid = hotelUuid;
    }
}
