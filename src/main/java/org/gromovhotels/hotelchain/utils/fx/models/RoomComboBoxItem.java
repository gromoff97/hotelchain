package org.gromovhotels.hotelchain.utils.fx.models;

import java.util.UUID;

public record RoomComboBoxItem(String roomType, UUID uuid) {
    @Override
    public String toString() {
        return roomType;
    }
}
