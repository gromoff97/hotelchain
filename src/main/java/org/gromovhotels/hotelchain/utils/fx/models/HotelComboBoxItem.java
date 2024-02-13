package org.gromovhotels.hotelchain.utils.fx.models;

import java.util.UUID;

public record HotelComboBoxItem(String hotelName, UUID hotelUuid) {
    @Override
    public String toString() {
        return hotelName;
    }
}
