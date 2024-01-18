package org.gromovhotels.hotelchain.booking;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.Collections.unmodifiableList;

/**
 * Нужен для манипуляци данными о бронированиях
 * @see Booking
 */
@Repository
public final class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    public void createBooking(Booking booking) {
        bookings.add(booking);
    }

    public void deleteBookingById(UUID uuid) {
        deleteBookingsSatisfying(booking -> booking.id().equals(uuid));
    }

    public void deleteBookingsSatisfying(Predicate<Booking> predicate) {
        bookings.removeIf(predicate);
    }

    public Optional<Booking> findBookingById(UUID uuid) {
        return bookings.stream().filter(booking -> booking.id().equals(uuid)).findAny();
    }

    public List<Booking> getBookings() {
        return unmodifiableList(bookings);
    }

    public void clear() {
        bookings.clear();
    }

    public List<Booking> findBookingsWithRoomId(UUID roomId) {
        return getBookings().stream().filter(booking -> booking.roomId().equals(roomId)).toList();
    }

    public List<Booking> findBookingsWithGuestId(UUID guestId) {
        return getBookings().stream().filter(booking -> booking.guestId().equals(guestId)).toList();
    }
}
