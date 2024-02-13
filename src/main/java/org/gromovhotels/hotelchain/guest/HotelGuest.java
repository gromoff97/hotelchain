package org.gromovhotels.hotelchain.guest;

import lombok.NonNull;
import org.apache.commons.text.TextStringBuilder;

import java.util.UUID;

/**
 * Экземпляр этого класса содержит информацию о госте отеля.
 * Класс важен не только для хранения состояния, но и для использования его в оконном и консольном приложениях.
 */
public record HotelGuest(@NonNull UUID id, @NonNull String name, @NonNull String passportNumber) {
    @Override
    public String toString() {
        return new TextStringBuilder()
                .append("Идентификатор гостя -> ").append(id).appendNewLine()
                .append("ФИО гостя -> ").append(name).appendNewLine()
                .append("Паспортные данные -> ").append(passportNumber).appendNewLine()
                .build();
    }
}
