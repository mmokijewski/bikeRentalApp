package com.mmokijewski.bikeRentalApp.service;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.BikeDto;

public interface BikeService {

    List<BikeDto> getAllBikes();

    BikeDto findById(final Long id);
}