package org.gromovhotels.hotelchain.graphicsapp.event.listeners.ui;

import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.ui.HotelComboboxUpdateRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class HotelComboboxUpdateRequestedListener extends AbstractApplicationListener<HotelComboboxUpdateRequestedEvent> {
    @Override
    public void onApplicationEvent(@NonNull HotelComboboxUpdateRequestedEvent event) {
        managementController.synchronizeHotelsComboboxes();
    }
}
