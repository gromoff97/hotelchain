package org.gromovhotels.hotelchain.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.booking.BookingRepository;
import org.gromovhotels.hotelchain.booking.HotelCheckRange;
import org.gromovhotels.hotelchain.guest.HotelGuestService;
import org.gromovhotels.hotelchain.room.HotelRoom;
import org.gromovhotels.hotelchain.room.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static org.gromovhotels.hotelchain.utils.ValidationUtils.*;

@Service
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class BookingCreationValidator implements ConstraintValidator<ValidatedBookingCreation, Object[]> {

    @Autowired HotelGuestService guestService;
    @Autowired HotelRoomService roomService;
    @Autowired BookingRepository bookingRepo;

    @Override
    public boolean isValid(Object[] params, ConstraintValidatorContext context) {
        UUID guestId = validateUUID(validateString(params[0]));
        UUID hotelRoomId = validateUUID(validateString(params[1]));
        LocalDate checkIn = validateIsAfterNow(validateLocalDate(validateString(params[2])));
        LocalDate checkOut = validateIsAfterNow(validateLocalDate(validateString(params[3])));

        guestService.getById(guestId.toString());
        HotelRoom room = roomService.getById(hotelRoomId.toString());

        HotelCheckRange range = new HotelCheckRange(checkIn, checkOut);
        List<HotelCheckRange> intersections = findHotelRangeIntersections(range, room);
        if (!intersections.isEmpty()) {
            String messageFormat = "Указанные даты заезда и выезда (%s) не подходят.\nПересекающиеся с этой датой заезды:\n%s";
            throw new IllegalArgumentException(messageFormat.formatted(range, intersections));
        }

        return true;
    }

    private List<HotelCheckRange> findHotelRangeIntersections(HotelCheckRange firstRange, HotelRoom room) {
        return bookingRepo.getBookings().stream()
                .filter(forRoom(room))
                .map(Booking::hotelCheckRange)
                .filter(firstRange::intersects)
                .toList();
    }

    private static Predicate<Booking> forRoom(HotelRoom room) {
        return booking -> booking.roomId().equals(room.id());
    }
}
