package com.mmokijewski.bikeRentalApp.exception;

public class NoSuchReservationException extends Exception {

    public NoSuchReservationException(final Long reservationId) {
        super(String.format("Reservation with id '%s' does not exist!", reservationId));
    }
}