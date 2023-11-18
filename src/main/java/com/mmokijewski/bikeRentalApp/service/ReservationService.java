package com.mmokijewski.bikeRentalApp.service;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.ReservationDto;
import com.mmokijewski.bikeRentalApp.exception.BikeNotAvailableException;

public interface ReservationService {

    List<ReservationDto> getAllReservations();

    ReservationDto findById(final Long id);

    ReservationDto createReservation(final Long bikeId, final Long cyclistId) throws BikeNotAvailableException;

    ReservationDto createReservation(final Long bikeId, final Long cyclistId, final int minutes)
            throws BikeNotAvailableException;
}