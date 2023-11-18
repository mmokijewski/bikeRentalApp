package com.mmokijewski.bikeRentalApp.repository;

import com.mmokijewski.bikeRentalApp.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}