package org.gromovhotels.hotelchain.booking;

import lombok.NonNull;
import lombok.With;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.room.HotelRoom;

import java.util.UUID;

/**
 * Экземпляр этого класса содержит информацию о бронировании.
 * Класс важен не только для хранения состояния, но и для использования его в оконном и консольном приложениях.
 *
 * @param id  уникальный идентификатор бронирования
 * @param guestId  гость, заказавший бронь
 * @param roomId  комната, выделенная гостю
 * @param hotelCheckRange дата заезда-выезда
 * @param bookingFee  счёт на оплату
 */
@With
public record Booking(@NonNull UUID id,
                      @NonNull UUID guestId,
                      @NonNull UUID roomId,
                      @NonNull HotelCheckRange hotelCheckRange,
                      @NonNull BookingFee bookingFee) { }
