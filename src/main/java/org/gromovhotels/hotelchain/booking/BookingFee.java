package org.gromovhotels.hotelchain.booking;

import lombok.NonNull;
import lombok.With;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * Контейнер для счёта на оплату
 *
 * @param paymentAmount сумма оплаты
 * @param paymentDate - дата оплаты
 */
@With
public record BookingFee(@NonNull BigInteger paymentAmount, LocalDate paymentDate) { }
