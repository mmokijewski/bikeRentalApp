package com.mmokijewski.bikeRentalApp.exception;

public class NoSuchBikeException extends Exception {

    public NoSuchBikeException(final Long bikeId) {
        super(String.format("Bike with id '%s' does not exist!", bikeId));
    }
}