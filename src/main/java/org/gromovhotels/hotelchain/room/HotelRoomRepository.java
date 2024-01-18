package org.gromovhotels.hotelchain.room;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.hotel.HotelRepository;
import org.gromovhotels.hotelchain.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.findSingleOrThrow;

/**
 * Нужен для манипуляци данными об комнатах отеля
 * @see Hotel
 * @see HotelRoom
 */
@Repository
public final class HotelRoomRepository {

    private final List<HotelRoom> hotelRooms = new ArrayList<>();

    public void createHotelRoom(@NonNull HotelRoom hotelRoom) {
        hotelRooms.add(hotelRoom);
    }

    public void deleteHotelRoomById(@NonNull UUID uuid) {
        hotelRooms.removeIf(uuidEquals(uuid));
    }

    public void updateHotelRoomById(@NonNull UUID uuid, @NonNull HotelRoom room) {
        HotelRoom foundRoom = findSingleOrThrow(hotelRooms, uuidEquals(uuid));
        if (foundRoom.id() != room.id()) {
            throw new IllegalArgumentException("Can't update roomId's id");
        }
        hotelRooms.remove(foundRoom);
        hotelRooms.add(room);
    }

    public Optional<HotelRoom> findById(UUID roomUuid) {
        return getHotelRooms().stream().filter(uuidEquals(roomUuid)).findAny();
    }

    public List<HotelRoom> getHotelRooms() {
        return unmodifiableList(hotelRooms);
    }

    private static Predicate<HotelRoom> uuidEquals(@NonNull UUID expectedUuid) {
        return room -> Objects.equals(room.id(), expectedUuid);
    }

    public void clear() {
        hotelRooms.clear();
    }
}
