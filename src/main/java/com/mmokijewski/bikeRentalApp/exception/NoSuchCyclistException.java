package com.mmokijewski.bikeRentalApp.exception;

public class NoSuchCyclistException extends Exception {

    public NoSuchCyclistException(final Long cyclistId) {
        super(String.format("Cyclist with id '%s' does not exist!", cyclistId));
    }
}