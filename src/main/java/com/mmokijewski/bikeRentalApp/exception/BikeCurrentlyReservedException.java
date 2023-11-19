package com.mmokijewski.bikeRentalApp.exception;

public class BikeCurrentlyReservedException extends Exception {

    public BikeCurrentlyReservedException(final Long bikeId) {
        super(String.format("Bike with id '%s' is currently reserved and can not be sent to service!", bikeId));
    }
}