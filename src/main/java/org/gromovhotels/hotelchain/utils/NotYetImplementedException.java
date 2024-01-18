package org.gromovhotels.hotelchain.utils;

public class NotYetImplementedException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "This method is not implemented yet";

    public NotYetImplementedException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public NotYetImplementedException(String message) {
        super(message);
    }
}
