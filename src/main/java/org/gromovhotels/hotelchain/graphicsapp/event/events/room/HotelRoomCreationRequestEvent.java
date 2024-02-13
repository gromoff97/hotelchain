package org.gromovhotels.hotelchain.graphicsapp.event.events.room;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class HotelRoomCreationRequestEvent extends ApplicationEvent {
    private final String hotelUuid;
    private final String roomType;

    public HotelRoomCreationRequestEvent(Object source, String hotelId, String roomType) {
        super(source);
        this.hotelUuid = hotelId;
        this.roomType = roomType;
    }
}
