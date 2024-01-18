package org.gromovhotels.hotelchain.utils;

import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.room.HotelRoom;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public final class Predicates {
    public static Predicate<HotelRoom> roomIsFromHotel(Hotel hotel) {
        return room -> hotel.roomIds().contains(room.id());
    }

    public static Predicate<Booking> bookingHasOneOfRoomsFrom(Set<HotelRoom> hotelRooms) {
        return booking -> hotelRooms.contains(booking.roomId());
    }

    public static Predicate<Hotel> hotelHasId(UUID uuid) {
        return hotel -> hotel.id().equals(uuid);
    }
}
