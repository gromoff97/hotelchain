package org.gromovhotels.hotelchain.hotel;

import org.gromovhotels.hotelchain.booking.BookingRepository;
import org.gromovhotels.hotelchain.collections.list.CycledLinkedList;
import org.gromovhotels.hotelchain.room.HotelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.gromovhotels.hotelchain.utils.GeneralUtils.findSingleOrThrow;
import static org.gromovhotels.hotelchain.utils.Predicates.*;

/**
 * Нужен для манипуляци данными об отелях
 * @see Hotel
 */
@Repository
public final class HotelRepository {
    private final CycledLinkedList<Hotel> hotels = new CycledLinkedList<>();

    @Autowired HotelRoomRepository roomRepository;
    @Autowired BookingRepository bookingRepository;

    public void createHotel(Hotel hotel) {
        hotels.insertInTheEnd(hotel);
    }

    public void deleteHotelById(UUID uuid) {
        hotels.removeIf(hotelHasId(uuid));
    }

    public void linkRoom(UUID hotelId, UUID roomId) {
        findSingleOrThrow(roomRepository.getHotelRooms(), room -> room.id().equals(roomId));
        findHotelById(hotelId).orElseThrow().roomIds().add(roomId);
    }

    public void unlinkRoom(UUID roomId) {
        findSingleOrThrow(roomRepository.getHotelRooms(), room -> room.id().equals(roomId));
        Hotel hotel = findSingleOrThrow(getHotels(), h -> h.roomIds().contains(roomId));
        hotel.roomIds().remove(roomId);
    }

    public List<Hotel> getHotels() {
        return hotels.stream().toList();
    }

    public void clear() {
        hotels.clear();
    }

    public Optional<Hotel> findHotelById(UUID uuid) {
        return hotels.stream().filter(hotel -> hotel.id().equals(uuid)).findAny();
    }

    public Optional<Hotel> findHotelByRoomId(UUID uuid) {
        return hotels.stream().filter(hotel -> hotel.roomIds().contains(uuid)).findAny();
    }
}
