package org.gromovhotels.hotelchain.room;

import jakarta.validation.constraints.NotBlank;
import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.booking.BookingRepository;
import org.gromovhotels.hotelchain.booking.BookingService;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.hotel.HotelRepository;
import org.gromovhotels.hotelchain.hotel.HotelService;
import org.gromovhotels.hotelchain.utils.GeneralUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.findSingleOrThrow;

/**
 * Содержит бизнес-логику для действий и проверок для комнат отелей
 * @see Hotel
 * @see HotelRoom
 */
@Service
@Validated
public class HotelRoomService {

    @Autowired private HotelRoomRepository hotelRoomRepository;
    @Autowired private HotelService hotelService;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private HotelRepository hotelRepository;

    public HotelRoom createHotelRoom(@NotBlank(message = "Название комнаты не должно быть пустым")
                                     @Length(min = 3, message = "Название типа комнаты слишком маленькое") String roomType,
                                     @NotBlank(message = "ID отеля не должен быть пустым")
                                     @UUID(message = "IO отеля должен быть UUID") String hotelUuid) {

        var foundHotel = findSingleOrThrow(hotelRepository.getHotels(), hotel -> hotel.id().toString().equals(hotelUuid));
        List<HotelRoom> foundHotelRooms = hotelRoomRepository.findRoomsByIds(foundHotel.roomIds());
        if (foundHotelRooms.stream().anyMatch(room -> room.roomType().equals(roomType))) {
            throw new IllegalArgumentException("В этом отеле уже существует комната '%s'".formatted(roomType));
        }

        HotelRoom hotelRoom = new HotelRoom(randomUUID(), roomType);
        hotelRoomRepository.createHotelRoom(hotelRoom);
        hotelService.linkRoom(hotelUuid, hotelRoom.id().toString());
        return hotelRoom;
    }

    public HotelRoom deleteHotelRoomById(@UUID(message = "Номер комнаты отеля должен быть UUID") String roomUuid) {
        List<Booking> bookings = bookingRepository.findBookingsWithRoomId(fromString(roomUuid));
        if (!bookings.isEmpty()) {
            throw new IllegalStateException("Нельзя удалить комнату с id = '%s': она ещё есть в бронированиях".formatted(roomUuid));
        }
        HotelRoom hotelRoom = hotelService.unlinkRoom(roomUuid);
        hotelRoomRepository.deleteHotelRoomById(fromString(roomUuid));
        return hotelRoom;
    }

    public HotelRoom getById(@NotBlank(message = "Номер комнаты отеля не должен быть пустым") @UUID(message = "Номер комнаты отеля должен быть UUID") String hotelRoomId) {
        var id = fromString(hotelRoomId);
        return hotelRoomRepository.findById(id).orElseThrow(couldNotFindRoomException(id));
    }

    private static Supplier<IllegalStateException> couldNotFindRoomException(java.util.UUID roomId) {
        return () -> new IllegalStateException("Не найдена комната с id = '%s'".formatted(roomId));
    }
}
