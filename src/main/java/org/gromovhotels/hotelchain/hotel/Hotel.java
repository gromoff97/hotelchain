package org.gromovhotels.hotelchain.hotel;

import lombok.NonNull;
import lombok.With;
import org.gromovhotels.hotelchain.room.HotelRoom;

import java.util.Set;
import java.util.UUID;

/**
 * Экземпляр этого класса содержит информацию об одном отеле во всей гостиничной системе.
 * Класс важен не только для хранения состояния, но и для использования его в оконном и консольном приложениях.
 */
@With
public record Hotel(@NonNull UUID id, @NonNull String name, @NonNull String address, @NonNull Set<UUID> roomIds) { }
