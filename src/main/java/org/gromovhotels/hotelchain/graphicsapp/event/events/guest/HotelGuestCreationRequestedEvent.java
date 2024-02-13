package org.gromovhotels.hotelchain.graphicsapp.event.events.guest;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class HotelGuestCreationRequestedEvent extends ApplicationEvent {

    @Getter private final String guestFullName;
    @Getter private final String guestPassportNumber;

    public HotelGuestCreationRequestedEvent(Object source, String guestFullName, String guestPassportNumber) {
        super(source);
        this.guestFullName = guestFullName;
        this.guestPassportNumber = guestPassportNumber;
    }
}
