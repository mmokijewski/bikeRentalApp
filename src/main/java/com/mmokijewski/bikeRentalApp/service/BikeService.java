package com.mmokijewski.bikeRentalApp.service;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;
import com.mmokijewski.bikeRentalApp.enums.BikeStatus;
import com.mmokijewski.bikeRentalApp.exception.BikeCurrentlyReservedException;
import com.mmokijewski.bikeRentalApp.exception.NoSuchBikeException;

public interface BikeService {

    List<BikeDto> getAllBikes();

    BikeDto findById(final Long id);

    BikeDto changeStatus(final Long id, final BikeStatus status) throws NoSuchBikeException;

    BikeDto sendToService(final Long id) throws NoSuchBikeException, BikeCurrentlyReservedException;
}