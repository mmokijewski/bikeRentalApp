package com.mmokijewski.bikeRentalApp.service;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.dto.CyclistDto;
import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;

public interface ReservationService {

    List<ReservationDto> getAllReservations();

    ReservationDto findById(final Long id);

    Reservation createReservation(final BikeDto bike, final CyclistDto cyclist) throws BikeNotAvailableException;

    Reservation createReservation(final BikeDto bike, final CyclistDto cyclist, final int minutes)
            throws BikeNotAvailableException;
}