package org.gromovhotels.hotelchain.utils.fx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import lombok.experimental.Accessors;
import org.apache.commons.text.TextStringBuilder;
import org.gromovhotels.hotelchain.booking.Booking;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.hotel.Hotel;
import org.gromovhotels.hotelchain.room.HotelRoom;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@With
public class BookingRow {

    private final UUID bookingId;
    private final String guestName;
    private final String hotelName;
    private final String roomType;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final BigInteger paymentAmount;
    private final LocalDate paymentDate;

    public BookingRow(Booking booking, HotelGuest guest, HotelRoom room, Hotel hotel) {
        bookingId = booking.id();
        guestName = guest.name();
        hotelName = hotel.name();
        roomType = room.roomType();
        checkIn = booking.hotelCheckRange().checkInDate();
        checkOut = booking.hotelCheckRange().checkOutDate();
        paymentAmount = booking.bookingFee().paymentAmount();
        paymentDate = booking.bookingFee().paymentDate();
    }

    @Override
    public String toString() {
        return new TextStringBuilder()
                .append("Идентификатор брони -> ").append(bookingId).appendNewLine()
                .append("ФИО гостя -> ").append(guestName).appendNewLine()
                .append("Название отеля -> ").append(hotelName).appendNewLine()
                .append("Тип комнаты в отеле -> ").append(roomType).appendNewLine()
                .append("Дата заезда в отель -> ").append(checkIn).appendNewLine()
                .append("Дата выезда из отеля -> ").append(checkOut).appendNewLine()
                .append("Сумма оплаты за проживание -> ").append(paymentAmount).appendNewLine()
                .append("Дата оплаты бронирования -> ").append(paymentDate == null ? "Отсуствует" : paymentDate).appendNewLine()
                .build();
    }
}
