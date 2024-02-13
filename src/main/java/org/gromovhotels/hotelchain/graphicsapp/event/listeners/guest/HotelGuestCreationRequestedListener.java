package org.gromovhotels.hotelchain.graphicsapp.event.listeners.guest;

import io.vavr.control.Try;
import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.guest.HotelGuestCreationRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

import static org.gromovhotels.hotelchain.utils.fx.Alerts.*;

@Component
public class HotelGuestCreationRequestedListener extends AbstractApplicationListener<HotelGuestCreationRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull HotelGuestCreationRequestedEvent event) {
        Callable<HotelGuest> createGuest = () -> hotelGuestService.createHotelGuest(event.getGuestFullName(), event.getGuestPassportNumber());
        Try.ofCallable(createGuest)
                .onSuccess(guest -> raiseGuestInfoAlert("Создан гость", guest))
                .onFailure(thr -> raiseErrorAlert("Ошибка создания гостя: %s".formatted(thr.getMessage())));
    }

}
