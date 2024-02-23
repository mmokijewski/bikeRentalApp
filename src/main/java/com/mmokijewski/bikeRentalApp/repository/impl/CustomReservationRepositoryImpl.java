package com.mmokijewski.bikeRentalApp.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mmokijewski.bikeRentalApp.entity.Reservation;
import com.mmokijewski.bikeRentalApp.repository.CustomReservationRepository;

import java.util.Optional;

public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<Reservation> findLastReservationByBikeId(final Long bikeId) {
        try {
            final TypedQuery<Reservation> query =
                    entityManager.createNamedQuery(Reservation.FIND_LAST_RESERVATION_BY_BIKE_ID, Reservation.class);
            query.setParameter("bikeId", bikeId);
            return Optional.of(query.getSingleResult());
        } catch (final NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Reservation> findLastReservation() {
        try {
            final TypedQuery<Reservation> query =
                    entityManager.createNamedQuery(Reservation.FIND_LAST_RESERVATION, Reservation.class);
            return Optional.of(query.getSingleResult());
        } catch (final NoResultException e) {
            return Optional.empty();
        }
    }
}