package org.gromovhotels.hotelchain.graphicsapp.event.events.booking;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public final class BookingCreationRequestedEvent extends ApplicationEvent {
    private final String guestFullName;
    private final String guestPassportNumber;
    private final String roomUuid;
    private final String checkInDate;
    private final String checkOutDate;
    private final boolean isPaid;

    public BookingCreationRequestedEvent(Object source,
                                         String guestFullName,
                                         String guestPassportNumber,
                                         String roomId,
                                         String checkInDate,
                                         String checkOutDate,
                                         boolean isPaid) {
        super(source);
        this.guestFullName = guestFullName;
        this.guestPassportNumber = guestPassportNumber;
        this.roomUuid = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.isPaid = isPaid;
    }
}
