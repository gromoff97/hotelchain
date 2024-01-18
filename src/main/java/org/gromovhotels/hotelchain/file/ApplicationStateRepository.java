package org.gromovhotels.hotelchain.file;

import org.gromovhotels.hotelchain.booking.BookingRepository;
import org.gromovhotels.hotelchain.guest.HotelGuestRepository;
import org.gromovhotels.hotelchain.hotel.HotelRepository;
import org.gromovhotels.hotelchain.room.HotelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationStateRepository {

    @Autowired HotelRepository hotelRepository;
    @Autowired HotelRoomRepository hotelRoomRepository;
    @Autowired HotelGuestRepository hotelGuestRepository;
    @Autowired BookingRepository bookingRepository;

    public ApplicationState getCurrentApplicationState() {
        return new ApplicationState(
                hotelRepository.getHotels(),
                hotelRoomRepository.getHotelRooms(),
                hotelGuestRepository.getHotelGuests(),
                bookingRepository.getBookings());
    }

    void applyApplicationStateToApp(ApplicationState applicationState) {
        validateApplicationState(applicationState);
        clearCurrentApplicationState();
        insertApplicationState(applicationState);
    }

    private void clearCurrentApplicationState() {
        hotelRepository.clear();
        hotelRoomRepository.clear();
        bookingRepository.clear();
        hotelGuestRepository.clear();
    }

    private void insertApplicationState(ApplicationState applicationState) {
        applicationState.hotels().forEach(hotelRepository::createHotel);
        applicationState.rooms().forEach(hotelRoomRepository::createHotelRoom);
        applicationState.guests().forEach(hotelGuestRepository::createGuest);
        applicationState.bookings().forEach(bookingRepository::createBooking);
    }

    private static void validateApplicationState(ApplicationState applicationState) {
        // У отелей уникальные идентификаторы
        // У комнат уникальные идентифкаторы
        // Нет номеров, которые не привязаны к отелю
    }
}
