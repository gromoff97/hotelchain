package org.gromovhotels.hotelchain.graphicsapp.event.listeners.hotel;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.hotel.HotelCreationRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseHotelInfoAlert;

@Component
public class HotelCreationRequestedListener extends AbstractApplicationListener<HotelCreationRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull HotelCreationRequestedEvent event) {
        Try.ofCallable(() -> hotelService.createHotel(event.getHotelName(), event.getHotelAddress()))
                .onSuccess(hotel -> {
                    eventPublisher.publishHotelComboboxUpdateRequestedEvent();
                    raiseHotelInfoAlert("Создана гостиница", hotel);
                })
                .onFailure(thr -> raiseErrorAlert("Ошибка создания гостиницы: %s".formatted(thr.getMessage())));
    }

}
