package org.gromovhotels.hotelchain.graphicsapp.event.listeners.room;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.room.HotelRoomCreationRequestEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseRoomInfoAlert;

@Component
public class HotelRoomCreationRequestListener extends AbstractApplicationListener<HotelRoomCreationRequestEvent> {

    @Override
    public void onApplicationEvent(@NonNull HotelRoomCreationRequestEvent event) {
        Try.ofCallable(() -> hotelRoomService.createHotelRoom(event.getRoomType(), event.getHotelUuid()))
                .onSuccess(room -> {
                    eventPublisher.publishHotelComboboxUpdateRequestedEvent();
                    raiseRoomInfoAlert("Была создана комната", room);
                })
                .onFailure(thr -> raiseErrorAlert("Ошибка создания комнаты: %s".formatted(thr.getMessage())));
    }

}
