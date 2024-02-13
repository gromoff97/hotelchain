package org.gromovhotels.hotelchain.graphicsapp.event.listeners;

import org.gromovhotels.hotelchain.booking.BookingService;
import org.gromovhotels.hotelchain.graphicsapp.controllers.BookingsTableController;
import org.gromovhotels.hotelchain.graphicsapp.controllers.ManagementController;
import org.gromovhotels.hotelchain.graphicsapp.event.UiAppEventPublisher;
import org.gromovhotels.hotelchain.guest.HotelGuestService;
import org.gromovhotels.hotelchain.hotel.HotelService;
import org.gromovhotels.hotelchain.room.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
abstract public class AbstractApplicationListener<T extends ApplicationEvent> implements ApplicationListener<T> {

    @Autowired protected BookingService bookingService;
    @Autowired protected UiAppEventPublisher eventPublisher;
    @Autowired protected BookingsTableController tableController;
    @Autowired protected ManagementController managementController;
    @Autowired protected HotelGuestService hotelGuestService;
    @Autowired protected HotelService hotelService;
    @Autowired protected HotelRoomService hotelRoomService;
}
