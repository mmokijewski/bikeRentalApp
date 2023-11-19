package com.mmokijewski.bikeRentalApp.service;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchBikeException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchCyclistException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchReservationException;

public interface ReservationService {

    List<ReservationDto> getAllReservations();

    ReservationDto findById(final Long id);

    ReservationDto createReservation(final Long bikeId, final Long cyclistId)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException;

    ReservationDto createReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException, NoSuchBikeException, NoSuchCyclistException;

    ReservationDto cancelReservation(final Long id) throws NoSuchReservationException;
}