package org.gromovhotels.hotelchain.hotel;

import lombok.NonNull;
import lombok.With;
import org.apache.commons.text.TextStringBuilder;
import org.gromovhotels.hotelchain.room.HotelRoom;

import java.util.Set;
import java.util.UUID;

/**
 * Экземпляр этого класса содержит информацию об одном отеле во всей гостиничной системе.
 * Класс важен не только для хранения состояния, но и для использования его в оконном и консольном приложениях.
 */
@With
public record Hotel(@NonNull UUID id, @NonNull String name, @NonNull String address, @NonNull Set<UUID> roomIds) {
    @Override
    public String toString() {
        return new TextStringBuilder()
                .append("Идентификатор отеля -> ").append(id).appendNewLine()
                .append("Имя отеля -> ").append(name).appendNewLine()
                .append("Адрес отеля -> ").append(address).appendNewLine()
                .build();
    }
}
