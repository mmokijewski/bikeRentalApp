package com.mmokijewski.bikeRentalApp.exception;

public class BikeNotAvailableException extends Exception {

    public BikeNotAvailableException(final String bikeId) {
        super(String.format("Bike %s is not available", bikeId));
    }
}