package com.mmokijewski.bikeRentalApp.repository;

import com.mmokijewski.bikeRentalApp.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

}