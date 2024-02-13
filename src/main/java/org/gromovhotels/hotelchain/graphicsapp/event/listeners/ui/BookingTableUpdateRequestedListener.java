package org.gromovhotels.hotelchain.graphicsapp.event.listeners.ui;

import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.ui.BookingTableUpdateRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.listeners.AbstractApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BookingTableUpdateRequestedListener extends AbstractApplicationListener<BookingTableUpdateRequestedEvent> {

    @Override
    public void onApplicationEvent(@NonNull BookingTableUpdateRequestedEvent event) {
        tableController.synchronizeTable();
    }

}
