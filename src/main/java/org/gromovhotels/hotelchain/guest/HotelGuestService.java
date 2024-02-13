package org.gromovhotels.hotelchain.guest;

import jakarta.validation.constraints.NotBlank;
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
import java.util.function.Consumer;

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

    public HotelGuest createHotelGuest(@NotBlank(message = "Имя гостя отеля не должно быть пустым")
                                       @Length(min = 3, max = 100, message = "Имя гостя отеля должно быть от 3 до 100 символов")
                                       String guestName,
                                 @NotBlank(message = "Паспортные данные не должны быть пустыми")
                                 @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Паспортные данные должны соответствовать формату 'dddd dddddd'") String passportNumber) {
        findByPassportNumber(passportNumber).ifPresent(throwGuestAlreadyExists);
        HotelGuest hotelGuest = new HotelGuest(randomUUID(), guestName, passportNumber);
        hotelGuestRepository.createGuest(hotelGuest);
        return hotelGuest;
    }

    public HotelGuest pollGuest() {
        HotelGuest guest = hotelGuestRepository.peekGuest().orElseThrow(() -> new IllegalStateException("Не был найден ни один гость"));
        List<Booking> bookingsWithGuest = bookingRepository.findBookingsWithGuestId(guest.id());
        if (!bookingsWithGuest.isEmpty()) {
            String messageFormat = "Нельзя удалить гостя с id = '%s' (имя: '%s', паспорт: '%s'), пока не будут удалены бронирования с ним";
            throw new IllegalStateException(messageFormat.formatted(guest.id(), guest.name(), guest.passportNumber()));
        }
        return hotelGuestRepository.pollGuest().orElseThrow(() -> new AssertionError("Гость был ранее найден"));
    }

    public HotelGuest getById(@UUID(message = "ID гостя должен быть UUID") String uuid) {
        return hotelGuestRepository.getHotelGuests().stream().filter(g -> g.id().equals(fromString(uuid))).findAny().orElseThrow();
    }

    public Optional<HotelGuest> findByPassportNumber(@Pattern(regexp = "^\\d{4} \\d{6}$", message = "Паспортные данные должны соответствовать формату 'dddd dddddd'") String passportNumber) {
        return hotelGuestRepository.getHotelGuests().stream().filter(g -> g.passportNumber().equals(passportNumber)).findAny();
    }

    private static final Consumer<HotelGuest> throwGuestAlreadyExists = guest -> {
        String m = "Гость с такими паспортными данными уже существует (Имя: %s, Паспортные данные: %s)";
        throw new IllegalArgumentException(m.formatted(guest.name(), guest.passportNumber()));
    };
}
