package com.mmokijewski.bikeRentalApp.service;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.dto.CyclistDto;
import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;

public interface ReservationService {

    Reservation createReservation(final BikeDto bike, final CyclistDto cyclist) throws BikeNotAvailableException;

    Reservation createReservation(final BikeDto bike, final CyclistDto cyclist, final int minutes)
            throws BikeNotAvailableException;
}