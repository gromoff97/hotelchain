package org.gromovhotels.hotelchain.graphicsapp.event.listeners.guest;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.guest.HotelGuestPollRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseErrorAlert;
import static org.gromovhotels.hotelchain.utils.fx.Alerts.raiseGuestInfoAlert;

@Component
public class HotelGuestPollRequestedListener extends AbstractApplicationListener<HotelGuestPollRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull HotelGuestPollRequestedEvent event) {
        Try.ofCallable(() -> hotelGuestService.pollGuest())
                .onSuccess(guest -> raiseGuestInfoAlert("'Вытащен' гость из очереди", guest))
                .onFailure(thr -> raiseErrorAlert("Ошибка 'вытаскивания' гостя: %s".formatted(thr.getMessage())));
    }

}
