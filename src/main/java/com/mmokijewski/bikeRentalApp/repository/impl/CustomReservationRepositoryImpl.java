package com.mmokijewski.bikeRentalApp.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.repository.CustomReservationRepository;

public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Reservation findLastReservationByBikeId(final Long bikeId) {
        final TypedQuery<Reservation> query =
                entityManager.createNamedQuery(Reservation.FIND_LAST_RESERVATION_BY_BIKE_ID, Reservation.class);
        query.setParameter("bikeId", bikeId);
        return query.getSingleResult();
    }

    @Override
    public Reservation findLastReservation() {
        final TypedQuery<Reservation> query =
                entityManager.createNamedQuery(Reservation.FIND_LAST_RESERVATION, Reservation.class);
        return query.getSingleResult();
    }
}