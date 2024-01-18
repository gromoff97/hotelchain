package org.gromovhotels.hotelchain.guest;

import lombok.NonNull;

import java.util.UUID;

/**
 * Экземпляр этого класса содержит информацию о госте отеля.
 * Класс важен не только для хранения состояния, но и для использования его в оконном и консольном приложениях.
 */
public record HotelGuest(@NonNull UUID id, @NonNull String name, @NonNull String passportNumber) { }
