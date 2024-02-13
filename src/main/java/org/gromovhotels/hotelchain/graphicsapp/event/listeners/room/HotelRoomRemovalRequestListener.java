package org.gromovhotels.hotelchain.graphicsapp.event.listeners.room;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.room.HotelRoomRemovalRequestEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseRoomInfoAlert;

@Component
public class HotelRoomRemovalRequestListener extends AbstractApplicationListener<HotelRoomRemovalRequestEvent> {

    @Override
    public void onApplicationEvent(@NonNull HotelRoomRemovalRequestEvent event) {
        Try.ofCallable(() -> hotelRoomService.deleteHotelRoomById(event.getRoomUuid()))
                .onSuccess(room -> {
                    eventPublisher.publishHotelComboboxUpdateRequestedEvent();
                    raiseRoomInfoAlert("Комната была удалена", room);
                })
                .onFailure(thr -> raiseErrorAlert("Ошибка удаления комнаты: %s".formatted(thr.getMessage())));
    }

}
