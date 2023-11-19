package com.mmokijewski.bikeRentalApp.repository;

import com.mmokijewski.bikeRentalApp.entity.Reservation;

public interface CustomReservationRepository {

    Reservation findLastReservationByBikeId(final Long bikeId);

    Reservation findLastReservation();
}