package org.gromovhotels.hotelchain.booking;

import lombok.NonNull;

import java.time.LocalDate;

/**
 * Контейнер для диапазона дат
 *
 * @param checkInDate дата заезда
 * @param checkOutDate дата выезда
 */
public record HotelCheckRange(@NonNull LocalDate checkInDate, @NonNull LocalDate checkOutDate) {

    public boolean intersects(HotelCheckRange otherRange) {
        return !(this.checkOutDate.isBefore(otherRange.checkInDate) || this.checkInDate.isAfter(otherRange.checkOutDate));
    }

    @Override
    public String toString() {
        return "%s -> %s".formatted(checkInDate, checkOutDate);
    }

    private boolean checkInBetweenIncluding(HotelCheckRange r) {
        return this.checkInDate.isAfter(r.checkInDate) && this.checkInDate.isBefore(r.checkOutDate);
    }

    private boolean checkOutBetweenIncluding(HotelCheckRange r) {
        return this.checkOutDate.isAfter(r.checkInDate) && this.checkOutDate.isBefore(r.checkOutDate);
    }
}
