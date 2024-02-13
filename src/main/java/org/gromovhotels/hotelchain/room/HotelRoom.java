package org.gromovhotels.hotelchain.room;

import lombok.NonNull;
import org.apache.commons.text.TextStringBuilder;

import java.util.UUID;

/**
 * Экземпляр этого класса содержит информацию о конретной отельной комнате.
 * Ассоциативен к {@link org.gromovhotels.hotelchain.hotel.Hotel}
 * Класс важен не только хранения состояния, но и для использования его в оконном и консольном приложениях.
 *
 * @param id уникальный идентификатор комнаты
 * @param roomType тип комнаты
 */
public record HotelRoom(@NonNull UUID id, @NonNull String roomType) {
    @Override
    public String toString() {
        return new TextStringBuilder()
                .append("Идентификатор комнаты -> ").append(id).appendNewLine()
                .append("Название комнаты -> ").append(roomType).appendNewLine()
                .build();
    }
}
