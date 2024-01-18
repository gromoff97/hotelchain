package org.gromovhotels.hotelchain.file;

import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.room.HotelRoom;

import java.util.List;

import static java.util.Collections.emptyList;

public record ApplicationState(List<Hotel> hotels, List<HotelRoom> rooms, List<HotelGuest> guests, List<Booking> bookings) {
    public static ApplicationState empty() {
        return new ApplicationState(emptyList(), emptyList(), emptyList(), emptyList());
    }
}
