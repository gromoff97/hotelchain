package org.gromovhotels.hotelchain.utils.fx.table;

import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.booking.BookingRepository;
import org.gromovhotels.hotelchain.utils.fx.models.BookingRow;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.guest.HotelGuestRepository;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.hotel.HotelRepository;
import org.gromovhotels.hotelchain.room.HotelRoom;
import org.gromovhotels.hotelchain.room.HotelRoomRepository;
import org.gromovhotels.hotelchain.utils.fx.models.HotelComboBoxItem;
import org.gromovhotels.hotelchain.utils.fx.models.RoomComboBoxItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
public class DataProvider {

    @Autowired BookingRepository bookingRepository;
    @Autowired HotelGuestRepository guestRepository;
    @Autowired HotelRoomRepository hotelRoomRepository;
    @Autowired HotelRepository hotelRepository;

    public List<BookingRow> currentBookingRecords() {
        return bookingRepository.getBookings().stream()
                .map(toBookingTableRecord)
                .toList();
    }

    public List<HotelComboBoxItem> currentHotelsItems() {
        return hotelRepository.getHotels().stream().map(toHotelItem).toList();
    }

    public List<RoomComboBoxItem> roomsForHotel(UUID hotelId) {
        return hotelRepository.findHotelById(hotelId).orElseThrow(AssertionError::new).roomIds().stream()
                .map(hotelRoomRepository::findById)
                .flatMap(Optional::stream)
                .map(toRoomItem)
                .toList();
    }

    private static final Function<HotelRoom, RoomComboBoxItem> toRoomItem = r -> new RoomComboBoxItem(r.roomType(), r.id());

    private static final Function<Hotel, HotelComboBoxItem> toHotelItem = h -> new HotelComboBoxItem(h.name(), h.id());

    private final Function<Booking, BookingRow> toBookingTableRecord = booking -> {
        HotelGuest guest = guestRepository.findGuestById(booking.guestId()).orElseThrow(AssertionError::new);
        HotelRoom room = hotelRoomRepository.findById(booking.roomId()).orElseThrow(AssertionError::new);
        Hotel hotel = hotelRepository.findHotelByRoomId(room.id()).orElseThrow(AssertionError::new);
        return new BookingRow(booking, guest, room, hotel);
    };
}
