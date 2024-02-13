package org.gromovhotels.hotelchain.booking;

import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingCreationRequestedEvent;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.guest.HotelGuestService;
import org.gromovhotels.hotelchain.room.HotelRoom;
import org.gromovhotels.hotelchain.room.HotelRoomService;
import org.gromovhotels.hotelchain.utils.validation.BookingCreationValidator;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;
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

    @Autowired private BookingCreationValidator bookingCreationValidator;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private HotelGuestService hotelGuestService;
    @Autowired private HotelRoomService hotelRoomService;

    public Booking createBooking(String guestUuid, String hotelRoomUuid, String checkIn, String checkOut, String paymentDate) {
        bookingCreationValidator.isValid(new Object[]{guestUuid, hotelRoomUuid, checkIn, checkOut, paymentDate}, null);
        HotelGuest guest = hotelGuestService.getById(guestUuid);
        HotelRoom room = hotelRoomService.getById(hotelRoomUuid);
        HotelCheckRange range = new HotelCheckRange(LocalDate.parse(checkIn), LocalDate.parse(checkOut));
        LocalDate payDate = paymentDate == null ? null : LocalDate.parse(paymentDate);
        BookingFee fee = new BookingFee(new BigInteger(Integer.toString(new Random().nextInt(100, 1001))), payDate);
        Booking booking = new Booking(randomUUID(), guest.id(), room.id(), range, fee);
        bookingRepository.createBooking(booking);
        return booking;
    }

    public Booking createBooking(BookingCreationRequestedEvent event) {
        Optional<HotelGuest> guestOpt = hotelGuestService.findByPassportNumber(event.getGuestPassportNumber());
        guestOpt.ifPresent((g) -> {
            if (!event.getGuestFullName().equals(g.name())) {
                String m = "Гость нашёлся по паспортным данным, но имя - другое (%s)";
                throw new IllegalStateException(m.formatted(g.name()));
            }
        });
        String guestId = guestOpt.orElseGet(() ->
                hotelGuestService.createHotelGuest(event.getGuestFullName(), event.getGuestPassportNumber())
        ).id().toString();
        String hotelRoomId = event.getRoomUuid();
        String checkIn = event.getCheckInDate();
        String checkOut = event.getCheckOutDate();
        String paymentDate = event.isPaid() ? LocalDate.now().toString() : null;
        return createBooking(guestId, hotelRoomId, checkIn, checkOut, paymentDate);
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
        bookingRepository.deleteBookingById(getById(uuid).id());
    }

    private static Supplier<IllegalStateException> couldNotFindBookingException(java.util.UUID bookingId) {
        return () -> new IllegalStateException("Не найдено бронирование с id = '%s'".formatted(bookingId));
    }
}