package com.mmokijewski.bikeRentalApp.exception;

public class BikeNotAvailableException extends Exception {

    public BikeNotAvailableException(final Long bikeId) {
        super(String.format("Bike with id '%s' is not available!", bikeId));
    }
}