package org.gromovhotels.hotelchain.hotel;

import org.gromovhotels.hotelchain.room.HotelRoomService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.function.Supplier;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

/**
 * Содержит бизнес-логику для действий и проверок для отелей
 * @see Hotel
 */
@Service
@Validated
public class HotelService {

    @Autowired private HotelRepository hotelRepository;

    public void createHotel(@Length(min = 3, message = "Имя отеля слишком короткое") String name, @Length(min = 3, message = "Адрес отеля слишком короткий") String address) {
        Hotel hotel = new Hotel(randomUUID(), name, address, new HashSet<>());
        hotelRepository.createHotel(hotel);
    }

    public void deleteHotelById(@UUID(message = "ID отеля должен быть UUID") String hotelUuid) {
        var id = fromString(hotelUuid);
        var hotel = hotelRepository.findHotelById(id).orElseThrow(couldNotFindHotelException(id));
        if (!hotel.roomIds().isEmpty()) {
            throw new IllegalStateException("В отеле с id = '%s' всё ещё есть номера.".formatted(id));
        }
        hotelRepository.deleteHotelById(id);
    }

    public void linkRoom(@UUID(message = "ID отеля должен быть UUID") String hotelUuid, @UUID(message = "ID комнаты должен быть UUID") String roomId) {
        var id = fromString(hotelUuid);
        hotelRepository.findHotelById(id).orElseThrow(couldNotFindHotelException(id));
        hotelRepository.linkRoom(id, fromString(roomId));
    }

    public void unlinkRoom(@UUID(message = "ID отеля должен быть UUID") String roomId) {
        var id = fromString(roomId);
        hotelRepository.findHotelByRoomId(id).orElseThrow(() -> new IllegalStateException("Не был найден отель с комнатой '%s'".formatted(id)));
        hotelRepository.unlinkRoom(id);
    }

    private static Supplier<IllegalStateException> couldNotFindHotelException(java.util.UUID hotelUuid) {
        return () -> new IllegalStateException("Не найден отель с id = " + hotelUuid);
    }
}
