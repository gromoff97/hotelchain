package org.gromovhotels.hotelchain.graphicsapp.event.events.room;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class HotelRoomRemovalRequestEvent extends ApplicationEvent {
    private final String roomUuid;

    public HotelRoomRemovalRequestEvent(Object source, String roomUuid) {
        super(source);
        this.roomUuid = roomUuid;
    }
}
