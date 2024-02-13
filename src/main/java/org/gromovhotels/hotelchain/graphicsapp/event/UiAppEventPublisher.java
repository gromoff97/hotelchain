package org.gromovhotels.hotelchain.graphicsapp.event;

import lombok.NonNull;
import org.gromovhotels.hotelchain.graphicsapp.event.events.guest.HotelGuestCreationRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingCreationRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingPaymentRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.booking.BookingRemovalRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.guest.HotelGuestPollRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.room.HotelRoomCreationRequestEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.room.HotelRoomRemovalRequestEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.ui.BookingTableUpdateRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.ui.HotelComboboxUpdateRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.hotel.HotelCreationRequestedEvent;
import org.gromovhotels.hotelchain.graphicsapp.event.events.hotel.HotelRemovalRequestedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UiAppEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public UiAppEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishBookingTableUpdateRequestedEvent() {
        eventPublisher.publishEvent(new BookingTableUpdateRequestedEvent(this));
    }

    public void publishHotelComboboxUpdateRequestedEvent() {
        eventPublisher.publishEvent(new HotelComboboxUpdateRequestedEvent(this));
    }

    public void publishCreateBookingEvent(String guestFullName, String passportNumber, String hotelRoomUuid, String checkIn, String checkOut, boolean isPaid) {
        eventPublisher.publishEvent(new BookingCreationRequestedEvent(this,
                guestFullName,
                passportNumber,
                hotelRoomUuid,
                checkIn,
                checkOut,
                isPaid));
    }

    public void publishPayBookingEvent(@NonNull String bookingId) {
        eventPublisher.publishEvent(new BookingPaymentRequestedEvent(this, bookingId));
    }

    public void publishDeleteBookingEvent(@NonNull String bookingId) {
        eventPublisher.publishEvent(new BookingRemovalRequestedEvent(this, bookingId));
    }

    public void publishCreateHotelEvent(String hotelUuid, String hotelAddress) {
        eventPublisher.publishEvent(new HotelCreationRequestedEvent(this, hotelUuid, hotelAddress));
    }

    public void publishHotelRemovalEvent(String hotelUuid) {
        eventPublisher.publishEvent(new HotelRemovalRequestedEvent(this, hotelUuid));
    }

    public void publishCreateHotelGuestEvent(String guestFullName, String guestPassportNumber) {
        eventPublisher.publishEvent(new HotelGuestCreationRequestedEvent(this, guestFullName, guestPassportNumber));
    }

    public void publishPollHotelGuestEvent() {
        eventPublisher.publishEvent(new HotelGuestPollRequestedEvent(this));
    }

    public void publishCreateRoomEvent(String hotelId, String roomType) {
        eventPublisher.publishEvent(new HotelRoomCreationRequestEvent(this, hotelId, roomType));
    }

    public void publishDeleteRoomEvent(String roomUuid) {
        eventPublisher.publishEvent(new HotelRoomRemovalRequestEvent(this, roomUuid));
    }
}
