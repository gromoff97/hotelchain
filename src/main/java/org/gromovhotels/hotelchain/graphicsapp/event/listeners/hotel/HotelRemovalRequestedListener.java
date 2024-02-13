package org.gromovhotels.hotelchain.graphicsapp.event.listeners.hotel;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.hotel.HotelRemovalRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseHotelInfoAlert;

@Component
public class HotelRemovalRequestedListener extends AbstractApplicationListener<HotelRemovalRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull HotelRemovalRequestedEvent event) {
        Try.ofCallable(() -> hotelService.deleteHotelById(event.getHotelUuid()))
                .onSuccess(hotel -> {
                    eventPublisher.publishHotelComboboxUpdateRequestedEvent();
                    raiseHotelInfoAlert("Удалена гостиница", hotel);
                })
                .onFailure(thr -> raiseErrorAlert("Ошибка удаления гостиницы: %s".formatted(thr.getMessage())));
    }

}
