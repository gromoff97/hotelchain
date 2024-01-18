package org.gromovhotels.hotelchain.guest;

import jakarta.validation.constraints.Pattern;
import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.booking.BookingRepository;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

/**
 * Содержит бизнес-логику для действий и проверок для гостей отеля
 * @see HotelGuest
 */
@Service
@Validated
public class HotelGuestService {

    @Autowired private HotelGuestRepository hotelGuestRepository;
    @Autowired private BookingRepository bookingRepository;

    public void createHotelGuest(@Length(min = 3, message = "Имя гостя отеля должено быть минимум из трёх символов") String hotelName,
                                 @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Паспортные данные должны соответствовать формату 'dddd dddddd'") String passportNumber) {
        hotelGuestRepository.createGuest(new HotelGuest(randomUUID(), hotelName, passportNumber));
    }

    public HotelGuest pollGuest() {
        HotelGuest a = hotelGuestRepository.peekGuest().orElseThrow(() -> new IllegalStateException("Не был найден ни один гость"));
        List<Booking> bookingsWithGuest = bookingRepository.findBookingsWithGuestId(a.id());
        if (!bookingsWithGuest.isEmpty()) {
            String messageFormat = "Нельзя удалить гостя с id = '%s', пока не будут удалены бронирования с ним";
            throw new IllegalStateException(messageFormat.formatted(a.id()));
        }
        return hotelGuestRepository.pollGuest().orElseThrow(() -> new AssertionError("Гость был ранее найден"));
    }

    public HotelGuest getById(@UUID(message = "ID гостя должен быть UUID") String uuid) {
        return hotelGuestRepository.getHotelGuests().stream().filter(g -> g.id().equals(fromString(uuid))).findAny().orElseThrow();
    }
}
