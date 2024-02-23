package com.mmokijewski.bikeRentalApp.repository;

import com.mmokijewski.bikeRentalApp.entity.Reservation;

import java.util.Optional;

public interface CustomReservationRepository {

    Optional<Reservation> findLastReservationByBikeId(final Long bikeId);

    Optional<Reservation> findLastReservation();
}