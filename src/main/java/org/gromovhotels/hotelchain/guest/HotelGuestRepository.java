package org.gromovhotels.hotelchain.guest;

import org.gromovhotels.hotelchain.collections.queue.ArrayQueue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Нужен для манипуляци данными о гостях отелй
 * @see HotelGuest
 */
@Repository
public final class HotelGuestRepository {
    private final ArrayQueue<HotelGuest> hotelGuestArrayQueue = new ArrayQueue<>();

    public void createGuest(HotelGuest hotelGuest) {
        hotelGuestArrayQueue.add(hotelGuest);
    }

    public Optional<HotelGuest> pollGuest() {
       return hotelGuestArrayQueue.poll();
    }

    public Optional<HotelGuest> peekGuest() {
        return hotelGuestArrayQueue.peek();
    }

    public Optional<HotelGuest> findGuestById(UUID guestId) {
        return getHotelGuests().stream().filter(hg -> hg.id().equals(guestId)).findAny();
    }

    public List<HotelGuest> getHotelGuests() {
        return hotelGuestArrayQueue.stream().toList();
    }

    public void clear() {
        hotelGuestArrayQueue.clear();
    }
}
