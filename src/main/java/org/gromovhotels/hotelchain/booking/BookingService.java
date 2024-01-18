package org.gromovhotels.hotelchain.booking;

import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.guest.HotelGuestService;
import org.gromovhotels.hotelchain.room.HotelRoom;
import org.gromovhotels.hotelchain.room.HotelRoomService;
import org.gromovhotels.hotelchain.utils.validation.ValidatedBookingCreation;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

/**
 * Содержит бизнес-логику для действий и проверок для бронирований
 * @see Booking
 */
@Service
@Validated
public class BookingService {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private HotelGuestService hotelGuestService;
    @Autowired private HotelRoomService hotelRoomService;

    @ValidatedBookingCreation
    public void createBooking(String guestUuid, String hotelRoomUuid, String checkIn, String checkOut) {
        HotelGuest guest = hotelGuestService.getById(guestUuid);
        HotelRoom room = hotelRoomService.getById(hotelRoomUuid);
        HotelCheckRange range = new HotelCheckRange(LocalDate.parse(checkIn), LocalDate.parse(checkOut));
        BookingFee fee = new BookingFee(new BigInteger(Integer.toString(new Random().nextInt(100, 1001))), null);

        Booking booking = new Booking(randomUUID(), guest.id(), room.id(), range, fee);
        bookingRepository.createBooking(booking);
    }

    public void payBooking(@UUID(message = "Идентификатор бронирования должен быть UUID") String bookingId) {
        Booking booking = getById(bookingId);
        if (booking.bookingFee().paymentDate() != null) {
            throw new IllegalStateException("Бронирование с id = '%s' уже оплачено".formatted(bookingId));
        }
        deleteBookingById(bookingId);
        BookingFee newBookingFee = booking.bookingFee().withPaymentDate(LocalDate.now());
        bookingRepository.createBooking(booking.withBookingFee(newBookingFee));
    }

    public Booking getById(@UUID(message = "Идентификатор бронирования должен быть UUID") String bookingId) {
        var id = fromString(bookingId);
        return bookingRepository.findBookingById(id).orElseThrow(couldNotFindBookingException(id));
    }

    public void deleteBookingById(@UUID(message = "Идентификатор бронирования должен быть UUID") String uuid) {
        bookingRepository.deleteBookingById(fromString(uuid));
    }

    private static Supplier<IllegalStateException> couldNotFindBookingException(java.util.UUID bookingId) {
        return () -> new IllegalStateException("Не найдено бронирование с id = '%s'".formatted(bookingId));
    }
}