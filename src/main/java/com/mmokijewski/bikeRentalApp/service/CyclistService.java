package com.mmokijewski.bikeRentalApp.service;

import java.util.List;

import com.mmokijewski.bikeRentalApp.dto.CyclistDto;

public interface CyclistService {

    List<CyclistDto> getAllCyclists();

    CyclistDto findById(final Long id);
}